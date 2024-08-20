import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger counter3 = new AtomicInteger(0);
    private static final AtomicInteger counter4 = new AtomicInteger(0);
    private static final AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        for (int i = 0; i < texts.length; i++) {
            int finalI = i;
            new Thread(() -> {
                if (isPretty(texts[finalI], 3))
                    counter3.incrementAndGet();
            }).start();
            new Thread(() -> {
                if (isPretty(texts[finalI], 4))
                    counter4.incrementAndGet();
            }).start();
            new Thread(() -> {
                if (isPretty(texts[finalI], 5))
                    counter5.incrementAndGet();
            }).start();
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

    public static boolean isPretty(String text, int length) {
        if (text.length() == length) {
            if (isPalindrome(text, length)) return true;
            if (isOneChar(text, length)) return true;
            return (isFloorAscending(text, length));
        }
        return false;
    }

    private static boolean isPalindrome(String text, int length) {
        for (int i = 0; i < (length / 2); i++) {
            if (text.charAt(i) != text.charAt(length - i - 1))
                return false;
        }
        return true;
    }

    private static boolean isOneChar(String text, int length) {
        for (int i = 0; i < length - 2; i++) {
            if (text.charAt(i) != text.charAt(i + 1))
                return false;
        }
        return true;
    }

    private static boolean isFloorAscending(String text, int length) {
        for (int i = 0; i < length - 2; i++) {
            if (text.codePointAt(i) > text.codePointAt(i + 1))
                return false;
        }
        return true;
    }
}