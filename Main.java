import java.io.*;
import java.util.*;

public class CalculatorApp {

    public static void main(String[] args) {
        // Створюємо колекцію для зберігання результатів обчислень
        List<ParametersAndResults> dataList = new ArrayList<>();

        // Додаємо результати обчислень до колекції
        ParametersAndResults data1 = new ParametersAndResults(new double[]{1.0, 2.0, 3.0}, new double[]{4.0, 5.0, 6.0});
        dataList.add(data1);

        ParametersAndResults data2 = new ParametersAndResults(new double[]{7.0, 8.0, 9.0}, new double[]{10.0, 11.0, 12.0});
        dataList.add(data2);

        // Зберігаємо та відновлюємо стан об'єкта з файлу
        saveAndRestoreObjectState(dataList);

        // Проводимо тестування коректності результатів та серіалізації/десеріалізації
        testCalculationAndSerialization();

        // Підрахунок кількості входжень кожної цифри у десятковому поданні цілого числа
        int number = 12345;
        int[] digitCounts = countDigits(number);

        System.out.println("Counts of digits in " + number + ":");
        for (int i = 0; i < digitCounts.length; i++) {
            System.out.println("Digit " + i + ": " + digitCounts[i]);
        }
    }

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

        public double[] getResults() {
            return results;
        }
    }

    // Функція для серіалізації та відновлення стану об'єкта з файлу
    private static void saveAndRestoreObjectState(List<ParametersAndResults> dataList) {
        try {
            // Зберігання об'єкта в файл
            FileOutputStream fileOut = new FileOutputStream("data.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(dataList);
            objectOut.close();
            fileOut.close();
            System.out.println("Object state saved successfully.");

            // Відновлення об'єкта з файлу
            FileInputStream fileIn = new FileInputStream("data.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            List<ParametersAndResults> restoredData = (List<ParametersAndResults>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Object state restored successfully.");

            // Вивід відновлених даних
            for (ParametersAndResults data : restoredData) {
                System.out.println("Parameters: " + Arrays.toString(data.getParameters()));
                System.out.println("Results: " + Arrays.toString(data.getResults()));
            }
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
}
