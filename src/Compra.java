import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

public class Compra {
    private List<Alimentacio> llistaAliments;
    private List<Textil> llistaTextils;
    private List<Electronica> llistaElectronics;

    public Compra() {
        this.llistaAliments = new ArrayList<>(100);
        this.llistaTextils = new ArrayList<>(100);
        this.llistaElectronics = new ArrayList<>(100);
    }

    public void afegirProducte(String tipusProducte) throws Exception {
        if (llistaElectronics.size() + llistaTextils.size() + llistaAliments.size() == 100) {
            registrarLog("No es poden afegir mes de 100 productes");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nAfegir " + tipusProducte);

        System.out.print("Nom producte: ");
        String nomProducte = sc.nextLine();

        if (nomProducte.length() >= 15) registrarLog("El nom no pot superar els 15 caracters");

        if (!(nomProducte.matches("[a-zA-Z]+"))) registrarLog("Nom incorrecte: " + nomProducte);

        nomProducte = nomProducte.substring(0, 1).toUpperCase() + nomProducte.substring(1).toLowerCase();

        System.out.print("Preu: ");
        String preuString = sc.next();

        float preu = 0;
        try {
            preu = Float.parseFloat(preuString);
        } catch (NumberFormatException e) {
            registrarLog("Preu incorrecte: " + preuString);
        }

        if (preu <= 0) registrarLog("Preu no pot ser inferior a 0");

        System.out.print("Codi de barres (6 caracters): ");
        String codiBarres = sc.next().toUpperCase();

        if (!(codiBarres.matches("[A-Z0-9]{6}"))) registrarLog("Codi de barres incorrecte: " + codiBarres);

        switch (tipusProducte) {
            case "Alimentacio" -> {
                System.out.print("Data caducitat (dd/mm/aaaa): ");
                String data = sc.next();

                LocalDate dataCaducitat = null;
                try {
                    dataCaducitat = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    registrarLog("Data introduida incorrecte: " + data);
                }
                llistaAliments.add(new Alimentacio(preu, nomProducte, codiBarres, dataCaducitat));
                break;
            }
            case "Textil" -> {
                System.out.print("Composició: ");
                String composicio = sc.next();

                if (!composicio.matches("[A-Z][a-z]+")) registrarLog("Composicio incorrecte: " + composicio);
                llistaTextils.add(new Textil(preu, nomProducte, codiBarres, composicio));
                break;
            }
            case "Electronica" -> {
                System.out.print("Dies garantia: ");
                String diesString = sc.next();
                int diesGarantia = 0;
                try {
                    diesGarantia = Integer.parseInt(diesString);
                } catch (NumberFormatException e) {
                    registrarLog("Dies de garantia incorrecte: " + diesString);
                }
                llistaElectronics.add(new Electronica(preu, nomProducte, codiBarres, diesGarantia));
                break;
            }
        }
    }

    public void passarPerCaixa() throws Exception {
        List<Producte> llistaProductes = new ArrayList<>();

        Set<Alimentacio> aliments = new HashSet<>(llistaAliments);
        aliments.forEach(llistaProductes::add);

        Set<Textil> textils = new HashSet<>(llistaTextils);
        textils.forEach(llistaProductes::add);

        Set<Electronica> electronics = new HashSet<>(llistaElectronics);
        electronics.forEach(llistaProductes::add);

        if (!llistaProductes.isEmpty()) {
            System.out.println('\n' + "-".repeat(20));
            System.out.println("SAPAMERCAT");
            System.out.println("-".repeat(20));
            System.out.println("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println("-".repeat(20));

            for (Producte producte : llistaProductes) {
                int unitats = 0;

                if (producte instanceof Alimentacio) {
                    unitats = Collections.frequency(llistaAliments, producte);
                } else if (producte instanceof Textil) {
                    comprovarPreuTextil(producte);
                    unitats = Collections.frequency(llistaTextils,producte);
                } else if (producte instanceof Electronica) {
                    unitats = Collections.frequency(llistaElectronics,producte);
                }

                System.out.println(producte.getNom() + "\t\t" + unitats + "\t\t" + producte.getPreu() + "\t\t" + (unitats * producte.getPreu()));

            }

            llistaAliments.clear();
            llistaTextils.clear();
            llistaElectronics.clear();

        } else registrarLog("El carret no pot estar buit");
    }

    public void mostarCarret() {
        List<Producte> llistaProductes = new ArrayList<>();
        Map<String, Integer> carret = new HashMap<>();

        llistaAliments.forEach(llistaProductes::add);
        llistaTextils.forEach(llistaProductes::add);
        llistaElectronics.forEach(llistaProductes::add);

        for (Producte producte : llistaProductes) {
            if (carret.containsKey(producte.getCodiBarres())) {
                carret.put(producte.getCodiBarres(), carret.get(producte.getCodiBarres()) + 1);
            } else {
                carret.put(producte.getCodiBarres(), 1);
            }
        }

        carret.forEach((k, v) -> System.out.println(buscarProducte(k) + ": " + v));
        carret.clear();
    }

    private String buscarProducte(String codiBarres) {
        Stream<Producte> streamProductes = Stream.concat(
                Stream.concat(llistaAliments.stream(), llistaTextils.stream()),
                llistaElectronics.stream());

        Optional<Producte> producteTrobat = streamProductes
                .filter(producte -> producte.getCodiBarres().equals(codiBarres))
                .findFirst();

        if (producteTrobat.isPresent()) {
            return producteTrobat.get().getNom();
        } else {
            return "No s'ha trobat el producte";
        }
    }

    private void comprovarPreuTextil(Producte producte) throws Exception {
        try {
            Scanner arxiuPreusTextil = new Scanner(new File("./updates/UpdateTextilPrices.dat"));

            while (arxiuPreusTextil.hasNextLine()) {
                String linia = arxiuPreusTextil.nextLine();
                String codiBarres = linia.split(";")[0];
                String preuCorrecte = linia.split(";")[1];

                if (codiBarres.equals(producte.getCodiBarres())) {
                    producte.setPreu(Float.parseFloat(preuCorrecte));
                    arxiuPreusTextil.close();
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            registrarLog("L'arxiu de preus de textil no s'ha trobat");
        }
    }

    private void registrarLog(String missatge) throws Exception {
        File directoriLogs = new File("./logs");
        File arxiuLogs = new File("./logs/Exceptions.log");

        if (!directoriLogs.exists()) {
            directoriLogs.mkdirs();
        }

        if (!arxiuLogs.exists()) {
            try {
                arxiuLogs.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FileWriter escritorArxiuLogs = new FileWriter(arxiuLogs,true);
                escritorArxiuLogs.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " - " + missatge + '\n');
                escritorArxiuLogs.close();
                throw new Exception("S'ha produit un error. Revisa el fitxer de logs");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
