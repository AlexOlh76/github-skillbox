import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction
 {

    private int beginRegionNumber;
    private int endRegionNumber;
    private  long start;

    public Task(int beginRegionNumber, int endRegionNumber, long start){

        this.beginRegionNumber = beginRegionNumber;
        this.endRegionNumber = endRegionNumber;
        this.start = start;
    }


    @Override
    protected void compute(){

        List<Task> listTask = new ArrayList<>();


        int regionCounter = endRegionNumber - (beginRegionNumber-1);

        if (regionCounter > Main.THRESHOLD) {


            // create tasks
            for (int i = beginRegionNumber - 1; i < endRegionNumber; i += Main.THRESHOLD) {

                int endNumber = endRegionNumber - i;
                if (endNumber < Main.THRESHOLD) {
                    endNumber = i + endNumber;
                } else {
                    endNumber = i + Main.THRESHOLD;
                }

                Task task = new Task(i + 1, endNumber, start);
                listTask.add(task);

            }

            //set task in ForkJoinPool
            for(Task task : listTask){

                task.fork();
            }


        } else {

            //running task in ForkJoinPool
            try {
                new Loader(beginRegionNumber, endRegionNumber).writer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }


        for(Task task : listTask){

            task.join();
            System.out.println((System.currentTimeMillis() - start) + "ms");

        }


    }

 }
