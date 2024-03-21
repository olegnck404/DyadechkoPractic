import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUI {

    private Calculator calculator = Calculator.getInstance();

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command [add/subtract/multiply/divide/undo/exit]:");
            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "add":
                        System.out.println("Enter first number:");
                        double a = scanner.nextDouble();
                        System.out.println("Enter second number:");
                        double b = scanner.nextDouble();
                        calculator.add(a, b);
                        break;
                    case "subtract":
                        System.out.println("Enter first number:");
                        a = scanner.nextDouble();
                        System.out.println("Enter second number:");
                        b = scanner.nextDouble();
                        calculator.subtract(a, b);
                        break;
                    case "multiply":
                        System.out.println("Enter first number:");
                        a = scanner.nextDouble();
                        System.out.println("Enter second number:");
                        b = scanner.nextDouble();
                        calculator.multiply(a, b);
                        break;
                    case "divide":
                        System.out.println("Enter first number:");
                        a = scanner.nextDouble();
                        System.out.println("Enter second number:");
                        b = scanner.nextDouble();
                        calculator.divide(a, b);
                        break;
                    case "undo":
                        calculator.undo();
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Unknown command.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Expected numerical value.");
                scanner.next(); // Clear input buffer
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
