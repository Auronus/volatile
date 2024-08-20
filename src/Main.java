import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger counter3 = new AtomicInteger(0);
    private static final AtomicInteger counter4 = new AtomicInteger(0);
    private static final AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threads = new ArrayList<>();
        Thread palindromeThread = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String currentText = texts[i];
                if (isPalindrome(currentText)) {
                    updateCounter(currentText);
                }
            }
        });
        Thread oneCharThread = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String currentText = texts[i];
                if (isOneChar(currentText)) {
                    updateCounter(currentText);
                }
            }
        });
        Thread floorAscendingThread = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String currentText = texts[i];
                if (isFloorAscending(currentText)) {
                    updateCounter(currentText);
                }
            }
        });

        palindromeThread.start();
        oneCharThread.start();
        floorAscendingThread.start();
        threads.add(palindromeThread);
        threads.add(oneCharThread);
        threads.add(floorAscendingThread);

        for (Thread thread : threads) {
            thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
        }
        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static boolean isPalindrome(String text) {
        for (int i = 0; i < (text.length() / 2); i++) {
            if (text.charAt(i) != text.charAt(text.length() - i - 1))
                return false;
        }
        return true;
    }

    private static boolean isOneChar(String text) {
        for (int i = 0; i < text.length() - 2; i++) {
            if (text.charAt(i) != text.charAt(i + 1))
                return false;
        }
        return true;
    }

    private static boolean isFloorAscending(String text) {
        for (int i = 0; i < text.length() - 2; i++) {
            if (text.codePointAt(i) > text.codePointAt(i + 1))
                return false;
        }
        return true;
    }

    private static void updateCounter(String currentText) {
        if (currentText.length() == 3) counter3.incrementAndGet();
        if (currentText.length() == 4) counter4.incrementAndGet();
        if (currentText.length() == 5) counter5.incrementAndGet();
    }
}