public class Interrupter implements Runnable {

    private Thread thread;

    public Interrupter(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
