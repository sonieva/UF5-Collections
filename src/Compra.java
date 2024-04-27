import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Compra {
    private List<Producte> llistaProductes;
    private List<Alimentacio> llistaAliments;
    private List<Textil> llistaTextils;
    private List<Electronica> llistaElectronics;

    public Compra() {
        this.llistaProductes = new ArrayList<>(100);
        this.llistaAliments = new ArrayList<>();
        this.llistaTextils = new ArrayList<>();
        this.llistaElectronics = new ArrayList<>();
    }

    public void afegirProducte(String tipusProducte) throws Exception {
        if (llistaProductes.size() == 100) {
            registrarLog("No es poden afegir mes de 100 productes");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nAfegir " + tipusProducte);

        System.out.print("Nom producte (15 caracters max.): ");
        String nomProducte = sc.nextLine();

        if (nomProducte.length() > 15) registrarLog("El nom no pot superar els 15 caracters");

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

        Producte producte = null;
        switch (tipusProducte) {
            case "Alimentacio" -> {
                System.out.print("Data caducitat (dd/mm/aaaa): ");
                String data = sc.next();

                LocalDate dataCaducitat = null;
                try {
                    dataCaducitat = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (dataCaducitat.isBefore(LocalDate.now())) registrarLog("La data introduida no pot ser abans de la data actual");

                } catch (DateTimeParseException e) {
                    registrarLog("Data introduida incorrecte: " + data);
                }

                producte = new Alimentacio(preu, nomProducte, codiBarres, dataCaducitat);
                llistaAliments.add((Alimentacio) producte);
                break;
            }
            case "Textil" -> {
                System.out.print("Composició: ");
                String composicio = sc.next();

                if (!composicio.matches("[A-Z][a-z]+")) registrarLog("Composicio incorrecte: " + composicio);

                producte = new Textil(preu, nomProducte, codiBarres, composicio);
                llistaTextils.add((Textil) producte);
                break;
            }
            case "Electronica" -> {
                System.out.print("Dies garantia: ");
                String diesString = sc.next();

                int diesGarantia = 0;
                try {
                    diesGarantia = Integer.parseInt(diesString);
                    if (diesGarantia < 0) registrarLog("Els dies de garantia no poden ser inferiors a 0");
                } catch (NumberFormatException e) {
                    registrarLog("Dies de garantia incorrecte: " + diesString);
                }

                producte = new Electronica(preu, nomProducte, codiBarres, diesGarantia);
                llistaElectronics.add((Electronica) producte);
                break;
            }
        }
        llistaProductes.add(producte);
    }

    public void passarPerCaixa() throws Exception {
        List<Producte> llistaProductesUnics = new ArrayList<>();

        Set<Alimentacio> aliments = new HashSet<>(llistaAliments);
        aliments.forEach(llistaProductesUnics::add);

        Set<Textil> textils = new HashSet<>(llistaTextils);
        textils.forEach(llistaProductesUnics::add);

        Set<Electronica> electronics = new HashSet<>(llistaElectronics);
        electronics.forEach(llistaProductesUnics::add);

        if (!llistaProductesUnics.isEmpty()) {
            String dataAvui = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            String separador = "-".repeat(60);

            System.out.println('\n' + separador);
            System.out.println(String.format("%35s","SAPAMERCAT"));
            System.out.println(separador);
            System.out.println(String.format("%1$39s\n%2$36s\n%3$28s%4$12s\n%5$38s\n%6$38s","C/ VILAR PETIT, 24","17300 BLANES","TELÈFON:","972350909",dataAvui,"L'ha atès: Santi"));
            System.out.println(separador);
            System.out.println(String.format("%1$-30s %2$2s %3$10s %4$15s","Nom","Qt","€/u","Total"));
            System.out.println(separador);

            float subtotal = 0;
            for (Producte producte : llistaProductesUnics) {
                int unitats = 0;
                float total;

                if (producte instanceof Alimentacio) {
                    unitats = Collections.frequency(llistaAliments, producte);
                } else if (producte instanceof Textil) {
                    comprovarPreuTextil(producte);
                    unitats = Collections.frequency(llistaTextils,producte);
                } else if (producte instanceof Electronica) {
                    unitats = Collections.frequency(llistaElectronics,producte);
                }

                if (unitats > 1) {
                    total = unitats * producte.getPreu();
                    System.out.println(String.format("%1$-30s %2$-3d %3$10.2f€ %4$12.2f€",producte.getNom(),unitats,producte.getPreu(),total));
                } else {
                    total = producte.getPreu();
                    System.out.println(String.format("%1$-30s %2$-3d %3$24.2f€",producte.getNom(),unitats,total));
                }
                subtotal += total;

            }
            System.out.println("-".repeat(60));
            System.out.println(String.format("Subtotal: %49.2f€ \n",subtotal));

            llistaAliments.clear();
            llistaTextils.clear();
            llistaElectronics.clear();

        } else registrarLog("El carret no pot estar buit");
    }

    public void mostarCarret() throws Exception {
        Map<String, Integer> carret = new HashMap<>();

        if (!llistaProductes.isEmpty()) {
            String separador = "-".repeat(34);

            System.out.println('\n' + separador);
            System.out.println(String.format("%20s","Carret"));
            System.out.println(separador);
            System.out.println(String.format("%1$-24s %2$s","Nom","Quantitat"));
            System.out.println(separador);

            for (Producte producte : llistaProductes) {
                if (carret.containsKey(producte.getCodiBarres())) {
                    carret.put(producte.getCodiBarres(), carret.get(producte.getCodiBarres()) + 1);
                } else {
                    carret.put(producte.getCodiBarres(), 1);
                }
            }

            carret.forEach((k, v) -> System.out.println(String.format("%1$-27s %2$6d",buscarProducte(k),v)));
            System.out.println(separador);
            System.out.println(String.format("%1$-27s %2$6d\n","Total productes unics:",carret.size()));
            carret.clear();

        } else registrarLog("El carret no pot estar buit");
    }

    private String buscarProducte(String codiBarres) {
        Optional<Producte> producteTrobat = llistaProductes.stream()
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

        do {
            directoriLogs.mkdirs();
        } while (!directoriLogs.exists());

        do {
            try {
                arxiuLogs.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (!arxiuLogs.exists());

        try {
            FileWriter escritorArxiuLogs = new FileWriter(arxiuLogs,true);
            escritorArxiuLogs.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " - " + missatge + '\n');
            escritorArxiuLogs.close();
            throw new Exception("S'ha produit un error. Revisa el fitxer de logs");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void omplirCarret() throws Exception {
        ArrayList<String> nomsAlimentacio = new ArrayList<>(Arrays.asList(
                "Arròs", "Mongetes", "Llet", "Pa", "Pomes", "Taronjes", "Iogurt", "Ous", "Pollastre", "Peix", "Oli", "Sucre", "Cafè", "Te", "Farina", "Sal", "Pebrera", "Formatge", "Mantega", "Sopa",
                "Cereals", "Pasta", "Tomàquet", "Ceba", "All", "Pastanagues", "Patates", "Api", "Pebró", "Espinacs", "Plàtans", "Rais", "Síndria", "Meló", "Pinya", "Peres", "Pressecs", "Mandonguilles", "Nous", "Cacauets",
                "Xocolata", "Mel", "Suc", "Refresc", "Vi", "Cervesa", "Whisky", "Ron", "Brandy", "Ginebra", "Vodka", "Tortilles", "Salsa", "Guacamole", "Tacos", "Burritos", "Enchiladas", "Tamales", "Pozole", "Mole",
                "Chiles en Nogada", "Chapulines", "Ceviche", "Tostades", "Sopes", "Empanades", "Arepa", "Pupusa", "Tamal", "Cachapa", "Pastís", "Galetes", "Llet Condensada", "Flan", "Tiramisú", "Gelat", "Pudín", "Crepes", "Bunyols", "Xurros",
                "Cotó de Moro", "Nachos", "Hot Dog", "Hamburguesa", "Perrito Caliente", "Sandvitx", "Amanida", "Sushi", "Tempura", "Ramen", "Sashimi", "Dim Sum", "Rolls de Primavera", "Pad Thai", "Curry"
        ));
        ArrayList<String> nomsTextil = new ArrayList<>(Arrays.asList(
                "Camisa", "Pantalons", "Vestit", "Jersei", "Faldilla", "Samarreta", "Sostèn", "Calces", "Sabatilles", "Guants", "Bufanda", "Gorra", "Abric", "Vestit de bany", "Braga", "Calçotets", "Pantalons curts", "Sàrongs", "Pijama", "Bata",
                "Roba interior", "Mitjons", "Vestit de nuvi", "Vestit de núvia", "Samarreta de seda", "Cravata", "Cinturó", "Jaqueta", "Vestit de vespre", "Vestit de nit", "Banyador", "Ranilla", "Vestit de còctel", "Armilla", "Ropa esportiva",
                "Jersei de coll alt", "Babador", "Barret", "Fulard", "Xancletes", "Bustier", "Lluïssos", "Gabardina", "Polsera", "Collaret", "Roba de llit", "Cotó", "Lli", "Seda", "Vellut", "Brim", "Bufanda de seda", "Pell", "Boto", "Cremallera",
                "Camíseta de tirants", "Bambes", "Calçons llargs", "Vestit de coctel", "Vestit de cocktail", "Guants de cuir", "Gorros de punt", "Vestit informal", "Roba de marca", "Ropa ecològica", "Sàrongs", "Samarreta de màniga llarga", "Vestit de platja",
                "Vestit de mariner", "Samarreta de ratlles", "Jersei de ratlles", "Jersei de punt", "Jersei de lana", "Jersei de llana", "Jersei de caxemira", "Vestit de punt", "Vestit de lli", "Roba de bany", "Roba esportiva", "Samarreta interior",
                "Vestit informal", "Vestit formal", "Roba de treball", "Uniforme escolar", "Uniforme laboral", "Ropa de festa", "Vestit de graduació", "Vestit de cerimònia", "Roba de cremallera", "Roba de butxaca"
        ));
        ArrayList<String> nomsElectronica = new ArrayList<>(Arrays.asList(
                "Telèfon mòbil", "Ordinador portàtil", "Tauleta", "Televisor", "Auriculars", "Ratolí", "Teclat", "Monitor", "Impressora", "Router", "Càmera", "Aparell de música", "Altaveu", "Aire condicionat",
                "Assecador de cabell", "Refrigerador", "Rentadora", "Forn", "Microones", "Aspiradora", "Robot aspirador", "Bateria externa", "Carregador", "Llum de lectura", "Relotge intel·ligent", "Baf",
                "Termòstat", "Barreja", "Proiector", "Auriculars sense fils", "Aparat de fax", "Termòmetre digital", "Videocàmera", "Joc de llums", "Despertador", "Plat giratori", "Humidificador", "Màquina d'espresso",
                "Smarthome", "Detector de fum", "Termòmetre infraroig", "Estufa elèctrica", "Dispensador d'aigua", "Calefactor", "Radiador", "Estació meteorològica", "Despertador lluminós", "Càmara de seguretat", "Cargol sense fil",
                "Bafle", "Barreja de so", "Llum LED", "Escrivania elèctrica", "Balança digital", "Vaporitzador", "Mini projector", "Termòstat intel·ligent", "Cafetera intel·ligent", "Cafetera programable", "Estufa intel·ligent",
                "Control remòt", "Ventilador de taula", "Ventilador de paret", "Ventilador de sostre", "Aire condicionat portàtil", "Aire condicionat finestra", "Aspiradora sense fils", "Calefactor portàtil", "Mini nevera"
        ));
        ArrayList<String> composicions = new ArrayList<>(Arrays.asList("Cotó", "Llana", "Seda", "Lli", "Polièster", "Niló", "Viscosa", "Acrílic", "Modal", "Bambú"));

        Random random = new Random();
        String nomAleatori;
        float preuAleatori;
        String caracters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int midaCarret = llistaAliments.size() + llistaTextils.size() + llistaElectronics.size();

        if (midaCarret == 100) {
            registrarLog("No es poden afegir mes de 100 productes");
        }

        System.out.println("Omplint carret");

        for (int i = 0; i < (100 - midaCarret); i++) {
            StringBuilder builderCodiBarres = new StringBuilder();
            for (int j = 0; j <= 6; j++) builderCodiBarres.append(caracters.charAt(random.nextInt(caracters.length())));
            String codiBarresAleatori = builderCodiBarres.toString();

            int limit = 3;
            if (!llistaProductes.isEmpty()) limit++;
            int tipusProducte = random.nextInt(1,limit) + 1;

            Producte producte = null;
            switch (tipusProducte) {
                case 1:
                    LocalDate dataAleatoria = LocalDate.of(random.nextInt(3000) + 1,random.nextInt(12) + 1,random.nextInt(28) + 1);
                    nomAleatori = nomsAlimentacio.get(random.nextInt(nomsAlimentacio.size()));
                    preuAleatori = random.nextFloat(8);

                    producte = new Alimentacio(preuAleatori,nomAleatori,codiBarresAleatori,dataAleatoria);
                    llistaAliments.add((Alimentacio) producte);
                    break;
                case 2:
                    String composicioAleatoria = composicions.get(random.nextInt(composicions.size()));
                    nomAleatori = nomsTextil.get(random.nextInt(nomsTextil.size()));
                    preuAleatori = random.nextFloat(110);

                    producte = new Textil(preuAleatori, nomAleatori, codiBarresAleatori, composicioAleatoria);
                    llistaTextils.add((Textil) producte);
                    break;
                case 3:
                    int diesGarantiaAleatori = random.nextInt(853);
                    nomAleatori = nomsElectronica.get(random.nextInt(nomsElectronica.size()));
                    preuAleatori = random.nextFloat(3500);

                    producte = new Electronica(preuAleatori,nomAleatori,codiBarresAleatori,diesGarantiaAleatori);
                    llistaElectronics.add((Electronica) producte);
                    break;
                case 4:
                    producte = llistaProductes.get(random.nextInt(llistaProductes.size()));
                    if (producte instanceof Alimentacio) {
                        llistaAliments.add((Alimentacio) producte);
                    } else if (producte instanceof Textil) {
                        llistaTextils.add((Textil) producte);
                    } else if (producte instanceof Electronica) {
                        llistaElectronics.add((Electronica) producte);
                    }
                    break;
            }
            llistaProductes.add(producte);
        }
    }

}
