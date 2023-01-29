public class Main {
    public static void main(String[] args) {

        Thread thread = new Processor();
        Thread interrupter = new Thread(new Interrupter(thread));

        thread.start();
        interrupter.start();
    }
}
