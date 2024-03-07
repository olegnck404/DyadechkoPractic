import java.io.*;
import java.util.*;

public class CalculatorApp {

    public static void main(String[] args) {
        // 1. Зберігання результатів обчислень у колекції

        // 1.1 Додати колекцію до класу ParametersAndResults
        class ParametersAndResults implements Serializable {

            private double[] parameters;
            private List<CalculationResult> results;

            public ParametersAndResults(double[] parameters, double[] results) {
                this.parameters = parameters;
                this.results = new ArrayList<>();
                for (double result : results) {
                    this.results.add(new CalculationResult("Default", result));
                }
            }

            public void addResult(CalculationResult result) {
                results.add(result);
            }

            public List<CalculationResult> getResults() {
                return results;
            }
        }

        // 1.2 Створити клас CalculationResult для зберігання результатів
        class CalculationResult implements Serializable {

            private String type;
            private double value;

            public CalculationResult(String type, double value) {
                this.type = type;
                this.value = value;
            }

            public String getType() {
                return type;
            }

            public double getValue() {
                return value;
            }
        }

        // 1.3 Зберігати об'єкти ParametersAndResults в ArrayList
        ArrayList<ParametersAndResults> dataList = new ArrayList<>();

        ParametersAndResults data1 = new ParametersAndResults(new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        dataList.add(data1);

        ParametersAndResults data2 = new ParametersAndResults(new double[]{7.0, 8.0, 9.0}, new double[]{10.0, 11.0, 12.0});
        dataList.add(data2);

        // 2. Розширення рахунку за допомогою Factory Method (Virtual Constructor)

        // 2.1 Створити базовий клас AbstractCalculator
        abstract class AbstractCalculator {

            public abstract double calculate(double a, double b);

            public String getType() {
                return "Abstract Calculator";
            }
        }

        // 2.2 Створити похідні класи для різних типів розрахунків
        class AdditionCalculator extends AbstractCalculator {

            @Override
            public double calculate(double a, double b) {
                return a + b;
            }

            @Override
            public String getType() {
                return "Addition Calculator";
            }
        }

        class MultiplicationCalculator extends AbstractCalculator {

            @Override
            public double calculate(double a, double b) {
                return a * b;
            }

            @Override
            public String getType() {
                return "Multiplication Calculator";
            }
        }

        // 2.3 Використовувати Factory Method для створення екземплярів калькуляторів
        public static AbstractCalculator createCalculator(String type) {
            switch (type) {
                case "Addition":
                    return new AdditionCalculator();
                case "Multiplication":
                    return new MultiplicationCalculator();
                default:
                    throw new IllegalArgumentException("Unknown calculator type: " + type);
            }
        }

        // 2.4 Приклад використання Factory Method
        AbstractCalculator calculator = createCalculator("Addition");
        double result = calculator.calculate(10, 20);
        System.out.println("Result: " + result);

        calculator = createCalculator("Multiplication");
        result = calculator.calculate(10, 20);
        System.out.println("Result: " + result);

        // 3. Розширення ієрархії інтерфейсом "фабрикованих" об'єктів

        // 3.1 Додати інтерфейс ResultFormatter
        interface ResultFormatter {

            String formatResult(CalculationResult result);
        }

        // 3.2 Реалізувати інтерфейс ResultFormatter для різних типів результатів
        class TextResultFormatter implements ResultFormatter {

            @Override
            public String formatResult(CalculationResult result) {
                return "Type: " + result.getType() + ", Value: " + result.getValue();
            }
        }

        // 4. Реалізувати методи виведення результатів у текстовому виде
        class HtmlResultFormatter implements ResultFormatter {

            @Override
            public String formatResult(CalculationResult result) {
                return "<html>" +
                        "<head><title>" + result.getType() + " Result</title></head>" +
                        "<body>" +
                        "<h1>" + result.getType() + "</h1>" +
                        "<p>Value: " + result.getValue() + "</p>" +
                        "</body>" +
                        "</html>";
            }
        }

        // 4. Реалізувати метод виведення результатів у табличному вигляді
        class TextTableFormatter implements ResultFormatter {
            private int columnWidth = 15; // Default column width

            // Constructor for customization
            public TextTableFormatter(int columnWidth) {
                this.columnWidth = columnWidth;
            }

            @Override
            public String formatResult(CalculationResult result) {
                StringBuilder sb = new StringBuilder();
                String headerFormat = "| %-" + columnWidth + "s | %-" + columnWidth + "s |\n";
                String lineSeparator = String.format("+%s+%-s+\n", "-".repeat(columnWidth), "-".repeat(columnWidth));

                sb.append(lineSeparator);
                sb.append(String.format(headerFormat, "Type", "Value"));
                sb.append(lineSeparator);
                sb.append(String.format(headerFormat, result.getType(), result.getValue()));
                sb.append(lineSeparator);
                return sb.toString();
            }
        }

        // 5. Розробити інтерфейс для "фабрикуючого" методу
        interface CalculatorFactory {

            AbstractCalculator createCalculator(String type);

            ResultFormatter createFormatter(String type); // New factory method
        }

        // 6. Розробити клас для тестування основної функціональності
        public static class CalculatorTester {
            public static void test() {
                // Add test cases here
            }
        }

        // 7. Використати коментарі для автоматичної генерації документації засобами javad
