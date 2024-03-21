import java.util.HashMap;
import java.util.Map;

public class DigitCounter {
    public static void main(String[] args) {
        int number = 1234567890; // Ціле число, для якого потрібно підрахувати цифри

        // Конвертуємо ціле число у рядок
        String numberString = String.valueOf(number);

        // Створюємо хеш-таблицю для підрахунку кількості входжень кожної цифри
        Map<Character, Integer> digitCounts = new HashMap<>();

        // Проходимо по кожному символу (цифрі) у рядку та підраховуємо їх кількість
        for (int i = 0; i < numberString.length(); i++) {
            char digit = numberString.charAt(i);
            // Якщо цифра вже зустрічалася, збільшуємо лічильник на 1
            if (digitCounts.containsKey(digit)) {
                digitCounts.put(digit, digitCounts.get(digit) + 1);
            } else {
                // Якщо цифра ще не зустрічалася, додаємо її до хеш-таблиці з лічильником 1
                digitCounts.put(digit, 1);
            }
        }

        // Виводимо результати підрахунку
        System.out.println("Кількість входжень кожної цифри у числі " + number + ":");
        for (Map.Entry<Character, Integer> entry : digitCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}