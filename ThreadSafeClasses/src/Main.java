import java.util.*;

public class Main {

    private static StringBuffer builder = new StringBuffer(); // вместо StringBuilder

    public static void main(String[] args) {

//        Hashtable<String,Integer> table = new Hashtable<>() ; // вместо HashMap
//        ArrayList<Thread> threads = new ArrayList<>(); // потокобезопасная коллекция


        ArrayList<Thread> threads = new ArrayList<>();
        for( int i = 0; i < 100; i++) {
            threads.add(new Thread(()->{
                for( int j = 0; j < 100000; j++) {
                    builder.append("d");
                }
                System.out.println(builder.length());
            }));

    }

//    private static Vector<Double> numbers = new Vector<>(); // Вместо ArrayList
//
//    public static void main(String[] args) {
//        ArrayList<Thread> threads = new ArrayList<>();
//        for( int i = 0; i < 100; i++) {
//            threads.add(new Thread(()->{
//                for( int j = 0; j < 100000; j++) {
//                    numbers.add(Math.random());
//                }
//                System.out.println(numbers.size());
//            }));
//        }

        threads.forEach(Thread::start);
    }
}
