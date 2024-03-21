import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CalculatorApp {

    private static final Calculator calculator = Calculator.getInstance();
    private static final ConsoleUI ui = new ConsoleUI();

    public static void main(String[] args) {
        ui.run();
    }

    private static class ParametersAndResults implements Serializable {

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

    private static class CalculationResult implements Serializable {

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

    private static abstract class AbstractCalculator {

        public abstract double calculate(double a, double b);

        public String getType() {
            return "Abstract Calculator";
        }
    }

    private static class AdditionCalculator extends AbstractCalculator {

        @Override
        public double calculate(double a, double b) {
            return a + b;
        }

        @Override
        public String getType() {
            return "Addition Calculator";
        }
    }

    private static class MultiplicationCalculator extends AbstractCalculator {

        @Override
        public double calculate(double a, double b) {
            return a * b;
        }

        @Override
        public String getType() {
            return "Multiplication Calculator";
        }
    }

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

    private static abstract class ResultFormatter {

        public abstract String formatResult(CalculationResult result);
    }

    private static class TextResultFormatter extends ResultFormatter {

        @Override
        public String formatResult(CalculationResult result) {
            return "Type: " + result.getType() + ", Value: " + result.getValue();
        }
    }

    private static class HtmlResultFormatter extends ResultFormatter {

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

    private static class TextTableFormatter extends ResultFormatter {
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

    private static class CalculatorFactory {

        public AbstractCalculator createCalculator(String type) {
            return createCalculator(type);
        }

        public ResultFormatter createFormatter(String type) {
            switch (type) {
                case "Text":
                    return new TextResultFormatter();
                case "Html":
                    return new HtmlResultFormatter();
                case "Table":
                    return new TextTableFormatter(20);
                default:
                    throw new IllegalArgumentException("Unknown formatter type: " + type);
            }
        }
    }

    private static abstract class Command {

        public abstract void execute();
    }

    private static abstract class UndoableCommand extends Command {

        public abstract void undo();
    }

    private static class CommandHistory {

        private List<Command> commands = new ArrayList<>();

        public void add(Command command) {
            commands.add(command);
        }

        public void undo() {
            if (!commands.isEmpty()) {
                Command command = commands.remove(commands.size() - 1);
                if (command instanceof UndoableCommand) {
                    ((UndoableCommand) command).undo();
                }
            }
        }

        public boolean canUndo() {
            return !commands.isEmpty();
        }
    }

    private static class Calculator {

        private static final Calculator instance = new Calculator();

        private CommandHistory history = new CommandHistory();

        private Calculator() {
        }

        public static Calculator getInstance() {
            return instance;
        }

        public void add(double a, double b) {
            Command command = new AddCommand(a, b);
            command.execute();
            history.add(command);
        }

        public void subtract(double a, double b) {
            Command command = new SubtractCommand(a, b);
            command.execute();
            history.add(command);
        }

        public void multiply(double a, double b) {
            Command command = new MultiplyCommand(a, b);
            command.execute();
            history.add(command);
        }

        public void divide(double a, double b) {
            Command command = new DivideCommand(a, b);
            command.execute();
            history.add(command);
        }

        public void undo() {
            history.undo();
        }

        public boolean canUndo() {
            return history.canUndo();
        }

        private static class AddCommand extends UndoableCommand {

            private double a;
            private double b;

            public AddCommand(double a, double b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public void execute() {
                double result = a + b;
                System.out.println("Result: " + result);
            }

            @Override
            public void undo() {
                double result = a + b; // Reversing addition operation
                System.out.println("Undo: " + result);
            }
        }

        private static class SubtractCommand extends UndoableCommand {

            private double a;
            private double b;

            public SubtractCommand(double a, double b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public void execute() {
                double result = a - b;
                System.out.println("Result: " + result);
            }

            @Override
            public void undo() {
                double result = a - b; // Reversing subtraction operation
                System.out.println("Undo: " + result);
            }
        }

        private static class MultiplyCommand extends UndoableCommand {

            private double a;
            private double b;

            public MultiplyCommand(double a, double b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public void execute() {
                double result = a * b;
                System.out.println("Result: " + result);
            }

            @Override
            public void undo() {
                double result = a * b; // Reversing multiplication operation
                System.out.println("Undo: " + result);
            }
        }

        private static class DivideCommand extends UndoableCommand {

            private double a;
            private double b;

            public DivideCommand(double a, double b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public void execute() {
                if (b != 0) {
                    double result = a / b;
                    System.out.println("Result: " + result);
                } else {
                    System.out.println("Cannot divide by zero.");
                }
            }

            @Override
            public void undo() {
                if (b != 0) {
                    double result = a / b; // Reversing division operation
                    System.out.println("Undo: " + result);
                } else {
                    System.out.println("Cannot divide by zero.");
                }
            }
        }
    }

    private static class ConsoleUI {

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

    private static class CalculatorTest {

        public static void test() {
            // Add tests here
        }
    }

    // Added code for parallel processing
    private ExecutorService executorService = Executors.newFixedThreadPool(4); // Thread pool

    public Future<Double> calculateMeanAsync(List<Double> values) {
        Callable<Double> task = () -> {
            double sum = 0;
            for (double value : values) {
                sum += value;
            }
            return sum / values.size();
        };
        return executorService.submit(task);
    }

    public Future<Double> findMinAsync(List<Double> values) {
        Callable<Double> task = () -> values.stream().min(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
        return executorService.submit(task);
    }

    public Future<Double> findMaxAsync(List<Double> values) {
        Callable<Double> task = () -> values.stream().max(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
        return executorService.submit(task);
    }

    public void shutdownExecutorService() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Added code for the "Worker Thread" pattern
    private static class TaskQueue {

        private static final TaskQueue instance = new TaskQueue();
        private BlockingQueue<String> tasks = new LinkedBlockingQueue<>();
        private WorkerThread workerThread;

        private TaskQueue() {
            workerThread = new WorkerThread();
            workerThread.start();
        }

        public static TaskQueue getInstance() {
            return instance;
        }

        public void addTask(String task) {
            tasks.add(task);
        }

        private class WorkerThread extends Thread {
            @Override
            public void run() {
                while (true) {
                    try {
                        String task = tasks.take(); // Blocking wait
                        processTask(task);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void processTask(String task) {
                // Implement task processing logic
            }
        }
    }
}

