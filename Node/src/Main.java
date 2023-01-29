import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        Node root = null;

        Long sum =new ForkJoinPool().invoke(new NodeValueSumCalculator(root));
        System.out.println(sum);
    }
}
