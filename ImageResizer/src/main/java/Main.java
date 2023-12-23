import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    private static int newWidth = 300;


    public static void main(String[] args) {

        String srcFolder = "C:/Users/Alex/Desktop/src";
        String dstFolder = "C:/Users/Alex/Desktop/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int numberProcessors = Runtime.getRuntime().availableProcessors();

        int item = files.length / numberProcessors;
        if (files.length % numberProcessors != 0) {
            item++;
        }

        File[]newFiles = new File[item*numberProcessors];
        System.arraycopy(files, 0, newFiles, 0, files.length);


        File[][] arrayFiles = new File[numberProcessors][item];

        for (int x = 0; x < numberProcessors; x++){
            System.arraycopy(newFiles, x*item, arrayFiles[x], 0, item);
            File[] array;
            if (x == (numberProcessors-1)) {
                int a = files.length % item;
                array = new File[a];
                for (int i = 0; i < a; i++) {
                    array[i] = arrayFiles[x][i];
                }
            } else {
                array = arrayFiles[x];
            }
            ImageResizer resizer = new ImageResizer(array, newWidth, dstFolder, start);
            resizer.start();
        }

    }
}
