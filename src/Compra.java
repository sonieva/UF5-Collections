import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            throw new Exception("No es poden afegir mes de 100 productes");
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("\nAfegir " + tipusProducte);

        String nomProducte;

        do {
            System.out.print("Nom producte: ");
            nomProducte = sc.nextLine();

            if (!(nomProducte.matches("[a-zA-Z]+"))) {
                System.out.println("Nom incorrecte. Torna a intentar.\n");
            }
        } while (!(nomProducte.matches("[a-zA-Z]+")));

        nomProducte = nomProducte.substring(0, 1).toUpperCase() + nomProducte.substring(1).toLowerCase();

        float preu;
        do {
            System.out.print("Preu: ");
            if (!(sc.hasNextFloat())) {
                System.out.println("Preu incorrecte. Torna a intentar.\n");
            }

            preu = sc.nextFloat();

            if (preu <= 0) {
                System.out.println("Preu no pot ser inferior a 0. Torna a intentar.\n");
            }
        } while (preu <= 0);

        String codiBarres;
        do {
            System.out.print("Codi de barres (6 caracters): ");
            codiBarres = sc.next().toUpperCase();

            if (!(codiBarres.matches("[A-Z0-9]{6}"))) {
                System.out.print("Codi de barres incorrecte. Torna a intentar.\n");
            }
        } while (!(codiBarres.matches("[A-Z0-9]{6}")));

        switch (tipusProducte) {
            case "Alimentacio" -> {
                System.out.print("Data caducitat (dd/mm/aaaa): ");
                LocalDate dataCaducitat = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                llistaAliments.add(new Alimentacio(preu, nomProducte, codiBarres, dataCaducitat));
                break;
            }
            case "Textil" -> {
                System.out.print("Composició: ");
                String composicio = sc.next();
                llistaTextils.add(new Textil(preu, nomProducte, codiBarres, composicio));
                break;
            }
            case "Electronica" -> {
                System.out.print("Dies garantia: ");
                int diesGarantia = Integer.parseInt(sc.next());
                llistaElectronics.add(new Electronica(preu, nomProducte, codiBarres, diesGarantia));
                break;
            }
        }
    }

    public void passarPerCaixa() {
        List<Producte> llistaProductes = new ArrayList<>();

        Set<Alimentacio> aliments = new HashSet<>(llistaAliments);
        aliments.forEach(llistaProductes::add);

        Set<Textil> textils = new HashSet<>(llistaTextils);
        textils.forEach(llistaProductes::add);

        Set<Electronica> electronics = new HashSet<>(llistaElectronics);
        electronics.forEach(llistaProductes::add);

        System.out.println('\n' + "-".repeat(20));
        System.out.println("SAPAMERCAT");
        System.out.println("-".repeat(20));
        System.out.println("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("-".repeat(20));

        for (Producte producte : llistaProductes) {
            int unitats = 0;
            comprovarPreu(producte);

            if (producte instanceof Alimentacio) {
                unitats = Collections.frequency(llistaAliments, producte);
            } else if (producte instanceof Textil) {
                unitats = Collections.frequency(llistaTextils,producte);
            } else if (producte instanceof Electronica) {
                unitats = Collections.frequency(llistaElectronics,producte);
            }

            System.out.println(producte.getNom() + "\t\t" + unitats + "\t\t" + producte.getPreu() + "\t\t" + (unitats * producte.getPreu()));

        }

        llistaAliments.clear();
        llistaTextils.clear();
        llistaElectronics.clear();
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

    private void comprovarPreu(Producte producte) {
        File arxiu = new File("../updates/UpdateTextilPrices.dat");;

        if (producte instanceof Alimentacio) {
            //arxiu = new File("../updates/UpdateAlimentacioPrices.dat");
        } else if (producte instanceof Textil) {
            arxiu = new File("../updates/UpdateTextilPrices.dat");
        } else if (producte instanceof Electronica) {
            //arxiu = new File("../updates/UpdateElectronicaPrices.dat");
        }

        Scanner scArxiu = null;
        try {
            scArxiu = new Scanner(arxiu);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scArxiu.hasNextLine()) {
            String codiBarres = scArxiu.nextLine().split(",")[0];
            String preuCorrecte = scArxiu.nextLine().split(",")[1];

            if (codiBarres.equals(producte.getCodiBarres())) {
                producte.setPreu(Float.parseFloat(preuCorrecte));
            }
        }
    }

}
