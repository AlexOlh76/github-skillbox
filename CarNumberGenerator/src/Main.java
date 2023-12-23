import java.util.concurrent.ForkJoinPool;

public class Main {

    // Maximum number of regions in the thread
    public static final int THRESHOLD = 50;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        // Начальный регион
        int beginRegionNumber = 1;

        // Конечный регион
        int endRegionNumber = 100;

        new ForkJoinPool().invoke(new Task(beginRegionNumber, endRegionNumber, start));


    }
}

