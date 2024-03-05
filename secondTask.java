import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class CombinedProgram {

    // Клас для зберігання параметрів та результатів обчислень
    public static class ParametersAndResults implements Serializable {
        private double[] parameters;
        private double[] results;

        public ParametersAndResults(double[] parameters, double[] results) {
            this.parameters = parameters;
            this.results = results;
        }

        public double[] getParameters() {
            return parameters;
        }

        public void setParameters(double[] parameters) {
            this.parameters = parameters;
        }

        public double[] getResults() {
            return results;
        }

        public void setResults(double[] results) {
            this.results = results;
        }
    }

    // Функція для серіалізації та відновлення стану об'єкта з файлу
    private static void trySaveAndRestoreObjectState() {
        try {
            // Створення об'єкта для зберігання
            ParametersAndResults dataToSave = new ParametersAndResults(new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
            // Збереження об'єкта в файл
            FileOutputStream fileOut = new FileOutputStream("data.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(dataToSave);
            objectOut.close();
            fileOut.close();
            System.out.println("Object state saved successfully.");

            // Відновлення об'єкта з файлу
            FileInputStream fileIn = new FileInputStream("data.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ParametersAndResults restoredData = (ParametersAndResults) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Object state restored successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Функція для тестування коректності результатів та серіалізації/десеріалізації
    private static void testCalculationAndSerialization() {
        // Реалізація тестувальної логіки
    }

    // Функція для підрахунку кількості входжень кожної цифри у десятковому поданні цілого числа
    public static int[] countDigits(int number) {
        int[] digitCounts = new int[10]; // Масив для зберігання кількості цифр 0-9

        if (number == 0) {
            digitCounts[0] = 1;  // Обробка спеціального випадку числа 0
        } else {
            while (number != 0) {
                int digit = number % 10;
                digitCounts[digit]++;
                number /= 10;
            }
        }

        return digitCounts;
    }

    public static void main(String[] args) {
        // Спроба збереження та відновлення стану об'єкта
        trySaveAndRestoreObjectState();

        // Тестування коректності результатів та серіалізації/десеріалізації
        testCalculationAndSerialization();

        // Підрахунок кількості входжень кожної цифри у десятковому поданні цілого числа
        int number = 12345;
        int[] digitCounts = countDigits(number);

        System.out.println("Counts of digits in " + number + ":");
        for (int i = 0; i < digitCounts.length; i++) {
            System.out.println("Digit " + i + ": " + digitCounts[i]);
        }
    }
}
