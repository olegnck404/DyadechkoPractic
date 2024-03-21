public class Calculator {

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
