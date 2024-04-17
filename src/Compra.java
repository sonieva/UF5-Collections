import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        System.out.println("Preu: ");
        float preu = sc.nextFloat();

        System.out.println("Codi de barres (xxxxxx): ");
        String codiBarres = sc.nextLine();

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

}
