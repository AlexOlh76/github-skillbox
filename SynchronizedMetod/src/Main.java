import java.util.ArrayList;

public class Main {

    private static ArrayList<Double> numbers = new ArrayList<>();

    public static void main(String[] args) {

        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            threads.add(new Thread(Main::someHeavyMetod));
        }

        threads.forEach(t -> t.start());

    }

    private static synchronized void someHeavyMetod() {

        for(int i = 0; i<100000; i++) {
            numbers.add(Math.random() / Math.random());
        }
        System.out.println(numbers.size());
        numbers.clear();
    }
}
