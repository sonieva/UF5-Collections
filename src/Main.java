import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int op,op2;
        String op3;

        Scanner sc = new Scanner(System.in);
        Compra compra = new Compra();

        try {
            do {
                System.out.println("\nBENVINGUT AL SAPAMERCAT");
                System.out.println("-".repeat(15));
                System.out.println("-".repeat(4) + " INICI " + "-".repeat(4));
                System.out.println("-".repeat(15));
                System.out.println("1. Introduir producte");
                System.out.println("2. Passar per caixa");
                System.out.println("3. Mostrar carret de compra");
                System.out.println("4. Omplir carret");
                System.out.println("0. Sortir");
                op = sc.nextInt();
                switch (op) {
                    case 1:
                        do {
                            System.out.println('\n' + "-".repeat(15));
                            System.out.println("-".repeat(2) + " PRODUCTE " + "-".repeat(3));
                            System.out.println("-".repeat(15));
                            System.out.println("1. Alimentació");
                            System.out.println("2. Tèxtil");
                            System.out.println("3. Electrònica");
                            System.out.println("0. Sortir");
                            op2 = sc.nextInt();
                            switch (op2) {
                                case 1:
                                    compra.afegirProducte("Alimentacio");
                                    break;
                                case 2:
                                    compra.afegirProducte("Textil");
                                    break;
                                case 3:
                                    compra.afegirProducte("Electronica");
                                    break;
                                default: break;
                            }
                        } while (op2 != 0);
                        break;
                    case 2:
                        compra.passarPerCaixa();
                        do {
                            System.out.print("Vols continuar comprant? (s/N) ");
                            op3 = sc.next().toUpperCase();
                            switch (op3) {
                                case "N":
                                    op = 0;
                                    System.out.println("Adeu, que tinguis un bon dia!");
                                    break;
                                default: break;
                            }
                        } while (!op3.equals("S") && !op3.equals("N"));
                        break;
                    case 3:
                        compra.mostarCarret();
                        break;
                    case 4:
                        compra.omplirCarret();
                        break;
                    case 0:
                        System.out.println("Programa finalitzat");
                    default: break;
                }
            } while (op != 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
