import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;


public class Main {

    public static Vector<String> listLink = new Vector<>();
    public static Vector<String> list = new Vector<>();


    public static String root;


    public static void main(String[] args) {

        root = "https://skillbox.ru/";
//        root = "https://lenta.ru/";



        new ForkJoinPool().invoke(new LinkOnSiteMap(root));

        sequencing();

        try {
            Files.write(Paths.get("data/file.txt"), list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sequencing() {

        ArrayList<Integer> indexList = new ArrayList<>();
        list.add(root);

        while (listLink.size() > 0) {

            for (int i = 0; i < listLink.size(); i++){
                String str = listLink.get(i);
                String[] fragments = str.split(" ");

                if ((fragments[0].equals(fragments[1]))){
                    indexList.add(i);
                } else {

                    for (int j = 0; j < list.size(); j++) {
                        String link = list.get(j);
                        String[] tab = link.split("https://");

                        boolean isLink = link.contains(fragments[0]);
                        if (isLink) {
                            int n = 0;
                            j++;
                            while (isLink && (j + n) <= (list.size() - 1)) {
                                link = list.get(j + n);
                                isLink = link.contains(fragments[0]);
                                if (isLink) {
                                    n++;
                                }
                            }
                            j = j + n;
                            str = tab[0].concat("\t").concat(fragments[1]);
                            list.add(j, str);
                            indexList.add(i);
                            break;
                        }
                    }
                }
            }

            for (int i = indexList.size() - 1; i >= 0; i--){
                int index = indexList.get(i);
                listLink.remove(index);
            }

            indexList.clear();
        }
    }
}
