import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class Compra {
    private List<Alimentacio> llistaAliments;
    private List<Textil> llistaTextils;
    private List<Electronica> llistaElectronics;

    public Compra() {
        this.llistaAliments = new ArrayList<>();
        this.llistaTextils = new ArrayList<>();
        this.llistaElectronics = new ArrayList<>();
    }

    public void afegirProducte(String tipusProducte) throws Exception {
        if (llistaElectronics.size() + llistaTextils.size() + llistaAliments.size() == 100) {
            throw new Exception("No es poden afegir mes de 100 productes");
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Afegir " + tipusProducte);

        System.out.println("Nom producte: ");
        String nomProducte = sc.nextLine();
        nomProducte = nomProducte.substring(0, 1).toUpperCase() + nomProducte.substring(1);

        System.out.println("Preu: ");
        float preu = sc.nextFloat();

        String codiBarres;
        do {
            System.out.println("Codi de barres (xxxxxx): ");
            codiBarres = sc.nextLine();
        } while (!(codiBarres.length() < 6));

        switch (tipusProducte) {
            case "Alimentacio" -> {
                System.out.println("Data caducitat (dd/mm/aaaa): ");
                LocalDate dataCaducitat = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                llistaAliments.add(new Alimentacio(preu,nomProducte,codiBarres,dataCaducitat));
            }
            case "Textil" -> {
                System.out.println("Composició: ");
                String composicio = sc.nextLine();
                llistaTextils.add(new Textil(preu,nomProducte,codiBarres,composicio));
            }
            case "Electronica" -> {
                System.out.println("Dies garantia: ");
                int diesGarantia = sc.nextInt();
                llistaElectronics.add(new Electronica(preu,nomProducte,codiBarres,diesGarantia));
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

        System.out.println("-".repeat(15));
        System.out.println("SAPAMERCAT");
        System.out.println("-".repeat(15));
        System.out.println("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println("-".repeat(15));

        for (Producte producte : llistaProductes) {
            int unitats = 0;

            if (producte instanceof Alimentacio) {
                unitats = Collections.frequency(llistaAliments, producte);
            } else if (producte instanceof Textil) {
                unitats = Collections.frequency(llistaTextils,producte);
            } else if (producte instanceof Electronica) {
                unitats = Collections.frequency(llistaElectronics,producte);
            }

            System.out.println(producte.getNom() + '\t' + unitats + '\t' + producte.getPreu() + '\t' + (unitats * producte.getPreu()));

        }

        llistaAliments.clear();
        llistaTextils.clear();
        llistaElectronics.clear();
    }

    public void mostarCarret() {
        System.out.println("Carret");

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

        Optional<Producte> producte = streamProductes
                .filter(producto -> producto.getCodiBarres().equals(codiBarres))
                .findFirst();

        return producte.get().getNom();
    }

}
