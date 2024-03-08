import java.io.*;
import java.util.*;

public class CalculatorApp {

    // Реалізація шаблону Singleton
    private static CalculatorApp instance;

    private CalculatorApp() {
        // Приватний конструктор, щоб уникнути створення екземплярів класу
    }

    public static CalculatorApp getInstance() {
        if (instance == null) {
            instance = new CalculatorApp();
        }
        return instance;
    }

    // Реалізація патерну Command для функціоналу скасування (undo)
    interface Command {
        void execute();
        void undo();
    }

    // Конкретна команда для операції додавання
    static class AdditionCommand implements Command {
        private final double a;
        private final double b;
        private final Calculator calculator;

        private double result;

        public AdditionCommand(double a, double b, Calculator calculator) {
            this.a = a;
            this.b = b;
            this.calculator = calculator;
        }

        @Override
        public void execute() {
            result = calculator.add(a, b);
        }

        @Override
        public void undo() {
            calculator.setCurrentValue(calculator.getCurrentValue() - result);
        }
    }

    // Конкретна команда для операції множення
    static class MultiplicationCommand implements Command {
        private final double a;
        private final double b;
        private final Calculator calculator;

        private double result;

        public MultiplicationCommand(double a, double b, Calculator calculator) {
            this.a = a;
            this.b = b;
            this.calculator = calculator;
        }

        @Override
        public void execute() {
            result = calculator.multiply(a, b);
        }

        @Override
        public void undo() {
            calculator.setCurrentValue(calculator.getCurrentValue() / result);
        }
    }

    // Клас для виконання обчислень
    static class Calculator {
        private double currentValue;

        public double getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(double currentValue) {
            this.currentValue = currentValue;
        }

        public double add(double a, double b) {
            return a + b;
        }

        public double multiply(double a, double b) {
            return a * b;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        while (true) {
            System.out.println("Введіть операцію ('+', '*', 'undo', 'exit'): ");
            String operation = scanner.nextLine();

            if ("exit".equals(operation)) {
                break;
            }

            if ("undo".equals(operation)) {
                // Скасування останньої операції
                // Для реалізації функціоналу скасування, потрібно зберігати стек виконаних команд
                // і видаляти останню команду для скасування
                // В цьому прикладі просто припускається, що скасування можна виконати один раз відразу після операції
                // У реальному випадку це потребуватиме більш складної реалізації
                System.out.println("Скасування останньої операції...");
                continue;
            }

            System.out.println("Введіть перше число: ");
            double operand1 = Double.parseDouble(scanner.nextLine());
            System.out.println("Введіть друге число: ");
            double operand2 = Double.parseDouble(scanner.nextLine());

            Command command;
            switch (operation) {
                case "+":
                    command = new AdditionCommand(operand1, operand2, calculator);
                    break;
                case "*":
                    command = new MultiplicationCommand(operand1, operand2, calculator);
                    break;
                default:
                    System.out.println("Неправильна операція!");
                    continue;
            }

            command.execute();
            System.out.println("Результат: " + calculator.getCurrentValue());
        }
    }

    // Реалізація макрокоманди
    static class MacroCommand implements Command {
        private final List<Command> commands = new ArrayList<>();

        public void addCommand(Command command) {
            commands.add(command);
        }

        @Override
        public void execute() {
            for (Command command : commands) {
                command.execute();
            }
        }

        @Override
        public void undo() {
            for (int i = commands.size() - 1; i >= 0; i--) {
                commands.get(i).undo();
            }
        }
    }

    // Тестовий клас для перевірки функціональності
    static class CalculatorTester {
        public static void test() {
            Calculator calculator = new Calculator();
            calculator.setCurrentValue(10);
            System.out.println("Поточне значення: " + calculator.getCurrentValue());

            Command additionCommand = new AdditionCommand(5, 3, calculator);
            additionCommand.execute();
            System.out.println("Після додавання: " + calculator.getCurrentValue());

            Command multiplicationCommand = new MultiplicationCommand(2, 3, calculator);
            multiplicationCommand.execute();
            System.out.println("Після множення: " + calculator.getCurrentValue());

            MacroCommand macroCommand = new MacroCommand();
            macroCommand.addCommand(additionCommand);
            macroCommand.addCommand(multiplicationCommand);
            macroCommand.execute();
            System.out.println("Після макрокоманди: " + calculator.getCurrentValue());

            macroCommand.undo();
            System.out.println("Після скасування: " + calculator.getCurrentValue());
        }
    }
}
