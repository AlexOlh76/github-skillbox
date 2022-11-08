import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("data/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(properties.getProperty("cacheSize"));

//        properties.setProperty("timeout","3600");
//        properties.setProperty("cacheSize", "10000");
//
//        try {
//            properties.store(new FileOutputStream("data/config.properties"), "");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
