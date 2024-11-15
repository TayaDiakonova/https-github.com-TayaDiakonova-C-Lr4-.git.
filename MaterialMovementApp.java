import java.util.InputMismatchException;
import java.util.Scanner;

class Material {  // Name of material warehouse +
    private String sklad;
    private double startBalance;
    private double received;
    private double issued;

    public Material(String sklad, double startBalance, double received, double issued) {
        this.sklad = sklad;
        this.startBalance = startBalance;
        this.received = received;
        this.issued = issued;
    }

    public String getSklad() {
        return sklad;
    }

    public double getStartBalance() {
        return startBalance;
    }

    public double getReceived() {
        return received;
    }

    public double getIssued() {
        return issued;
    }

    public double calculateEndBalance() {
        return startBalance + received - issued;
    }
}

class Inventory {
    private Material[] materials;

    public Inventory(int size) {
        materials = new Material[size];
    }

    public void addMaterial(Material material, int index) {
        materials[index] = material;
    }

    public void printTable() {
        System.out.println("+------------+------------+------------+------------+------------+");
        System.out.println("| Склад      | Початок    | Отримано   | Видано     | Кiнець      |");
        System.out.println("+------------+------------+------------+------------+------------+");

        double totalStartBalance = 0;
        double totalReceived = 0;
        double totalIssued = 0;
        double totalEndBalance = 0;

        for (Material material : materials) {
            if (material != null) {
                totalStartBalance += material.getStartBalance();
                totalReceived += material.getReceived();
                totalIssued += material.getIssued();
                totalEndBalance += material.calculateEndBalance();

                System.out.format("| %-10s | %10.2f | %10.2f | %10.2f | %10.2f |\n",
                        material.getSklad(),
                        material.getStartBalance(),
                        material.getReceived(),
                        material.getIssued(),
                        material.calculateEndBalance());
            }
        }

        System.out.println("+------------+------------+------------+------------+------------+");
        System.out.format("| Разом      | %10.2f | %10.2f | %10.2f | %10.2f |\n",
                totalStartBalance, totalReceived, totalIssued, totalEndBalance);
        System.out.println("+------------+------------+------------+------------+------------+");
    }
}

public class MaterialMovementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;

        while (true) {
            try {
                System.out.print("Введiть кiлькiсть записiв у вiдомостi: ");
                n = scanner.nextInt();
                if (n <= 0) {
                    System.out.println("Кiлькiсть записiв повинна бути позитивною.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Будь ласка, введiть коректне число.");
                scanner.next();
            }
        }

        Inventory inventory = new Inventory(n);

        for (int i = 0; i < n; i++) {
            System.out.println("Введiть данi для запису " + (i + 1));
            String sklad = "";
            double startBalance = 0, received = 0, issued = 0;

            while (true) {
                try {
                    System.out.print("Склад: ");
                    sklad = scanner.next();
                    System.out.print("Залишок на початок перiоду: ");
                    startBalance = scanner.nextDouble();
                    if (startBalance < 0) {
                        System.out.println("Залишок не може бути вiд'ємним. Спробуйте ще раз.");
                        continue;
                    }
                    System.out.print("Отримано: ");
                    received = scanner.nextDouble();
                    if (received < 0) {
                        System.out.println("Отримане значення не може бути вiд'ємним. Спробуйте ще раз.");
                        continue;
                    }
                    System.out.print("Видано: ");
                    issued = scanner.nextDouble();
                    if (issued < 0) {
                        System.out.println("Виданi матерiали не можуть бути вiд'ємними. Спробуйте ще раз.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Будь ласка, введiть коректнi числовi данi.");
                    scanner.next();
                }
            }

            Material material = new Material(sklad, startBalance, received, issued);
            inventory.addMaterial(material, i);
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\nМеню:");
            System.out.println("1. Вивести всi данi вiдомостi");
            System.out.println("2. Завершити програму");
            System.out.print("Виберiть опцiю: ");
            int option;

            while (true) {
                try {
                    option = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Будь ласка, введiть коректну опцiю.");
                    scanner.next();
                }
            }

            switch (option) {
                case 1:
                    inventory.printTable();
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    System.out.println("Невiрна опцiя. Спробуйте ще раз.");
            }
        }

        scanner.close();
    }
}
