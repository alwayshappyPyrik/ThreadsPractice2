import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> generateRoute("RLRFR", 10)).start();
        }
    }

    public static void generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        String finalString = null;
        for (int i = 0; i < length; i++) {
            finalString = String.valueOf(route.append(letters.charAt(random.nextInt(letters.length()))));
        }
        countOccurrences(finalString, 'R');
    }

    public static void countOccurrences(String str, char ch) {
        int counterR = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                counterR++;
            }
        }
        synchronized (sizeToFreq) {
            int counterString = 1;
            if (sizeToFreq.containsValue(counterR)) {
                sizeToFreq.put(++counterString, counterR);
            } else {
                sizeToFreq.put(1, counterR);
            }
        }
        printMap(sizeToFreq);
    }

    public static void printMap(Map<Integer, Integer> sizeToFreq) {
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println("-" + entry.getValue() + " (" + entry.getKey() + " раз)");
        }
    }
}
