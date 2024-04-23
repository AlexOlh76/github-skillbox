import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


public class Main {

//    public static List<String> listLinks = new ArrayList<>();
    public static List<String> pagelist = new ArrayList<>();
    public static List<String> listFile = new ArrayList<>();
    public static String root;


    public static void main(String[] args) {

        root = "https://lenta.ru/";


//        String page = webPage;//.substring(0, webPage.length() - 1);
        Integer count = new ForkJoinPool().invoke(new ListPage(root, root, 0));
        System.out.println(count);
    }


}
