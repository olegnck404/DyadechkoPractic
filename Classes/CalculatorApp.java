import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculatorApp {

    private static final Calculator calculator = Calculator.getInstance();
    private static final ConsoleUI ui = new ConsoleUI();

    public static void main(String[] args) {
        ui.run();
    }
}