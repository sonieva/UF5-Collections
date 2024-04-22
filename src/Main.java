import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int op,op2;

        Scanner sc = new Scanner(System.in);
        Compra compra = new Compra();

        do {
            System.out.println("\nBENVINGUT AL SAPAMERCAT");
            System.out.println("-".repeat(15));
            System.out.println("-".repeat(4) + " INICI " + "-".repeat(4));
            System.out.println("-".repeat(15));
            System.out.println("1. Introduir producte");
            System.out.println("2. Passar per caixa");
            System.out.println("3. Mostrar carret de compra");
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
                                try {
                                    compra.afegirProducte("Alimentacio");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                try {
                                    compra.afegirProducte("Textil");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                try {
                                    compra.afegirProducte("Electronica");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            default: break;
                        }
                    } while (op2 != 0);
                    break;
                case 2:
                    compra.passarPerCaixa();
                    break;
                case 3:
                    System.out.println("\nCarret");
                    compra.mostarCarret();
                    break;
                case 0:
                    System.out.println("Programa finalitzat");
                default: break;
            }
        } while (op != 0);
    }
}
