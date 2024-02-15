import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread logic = new Thread(() -> {
                String string = generateRoute("RLRFR", 100);
                int counterR = countOccurrences(string, 'R');
                updateMap(counterR);
            });
            threads.add(logic);
            logic.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printMap(sizeToFreq);

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countOccurrences(String str, char symbol) {
        int counterR = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == symbol) {
                counterR++;
            }
        }
        return counterR;
    }

    public static void updateMap(int counterR) {
        synchronized (sizeToFreq) {
            sizeToFreq.merge(counterR, 1, Integer::sum);
        }
    }

    public static void printMap(Map<Integer, Integer> sizeToFreq) {
        Optional<Map.Entry<Integer, Integer>> maxEntry = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue());
        int maxValue = maxEntry.get().getKey();
        System.out.println("Самое частое количество повторений " + maxValue + " (встретилось " + maxEntry.get().getValue() + " раз)");
        System.out.println("Другие размеры: ");
        sizeToFreq.forEach((repeats, count) -> System.out.println("-" + repeats + " (" + count + " раз)"));
    }

}




