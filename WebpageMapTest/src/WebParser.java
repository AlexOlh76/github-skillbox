import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ListPage {

    ArrayList<String> list = new ArrayList<>();

    public String previousPage;
    public String page;
    public int tab;


    public ListPage(String previousPage, String page, int tab){
        this.previousPage = previousPage;
        this.page = page;
        this.tab = tab;
    }

    protected Integer compute() {
        int count = 1;
        if(tab != 0) {
            tab++;
        }

        List<ListPage> taskList = new ArrayList<>();

        parsingWebPage(page);

        if (list.size() == 0) {
            addListFile(previousPage, page, tab);
        } else {
            for (String line : list) {
                boolean isVal;
                String pageDo = page;
                String previousPageDo = previousPage;
                int tabDo = tab;
                do {
                    if (!line.contains(pageDo)) {
                        if (!line.contains("https://") && line.length() > 1) {
                            line = pageDo + line.substring(1);
                        } else {
                            line = pageDo;
                        }
                    }

                    String nextPage = nextPage(line, pageDo);
                    addListFile(previousPageDo, pageDo, tabDo);
                    if(!pageDo.equals(page)) {
//                        ListPage task = new ListPage(previousPageDo, pageDo, tabDo);
//                        task.fork();
//                        taskList.add(task);
                    }

                    isVal = nextPage.equals(pageDo);
                    previousPageDo = pageDo;
                    pageDo = nextPage;
                    tabDo += 1;
                } while (!isVal);
            }
        }


//        for (ListPage task : taskList){
//            count += task.join();
//        }
        return count;
    }

    public void parsingWebPage(String page) {
        Document doc = null;
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doc = Jsoup.connect(page).get();
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        Elements links = doc.select("a");
        links.forEach(link -> {
            String line = link.attr("href");
            list.add(line);
        });

        list.forEach(link -> {
            System.out.println(link);
        });
        System.out.println(list.size());
    }

    public String nextPage(String line, String page){

        line = line.replaceFirst(page,"");
        try {
            if (line.length() == 0) {
                return page;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String[] fragments = line.split("/");
        int index = fragments.length;
        if(index == 0){
            return page;
        }

        String fragment = fragments[0];
        return page + fragment + "/";

    }

//    public void addPage(String previousPage, String page, int tab) {
//
//        if(!Main.pagelist.contains(page)){
//            Main.pagelist.add(page);
//            addListFile(previousPage, page, tab);
//        }
//    }

    public void addListFile(String previousPage, String page, int tab){

        List<String> strList;
        if (Main.listFile.isEmpty()){
            Main.listFile.add(previousPage);
        } else if (!previousPage.equals(page)) {

            for (String line : Main.listFile) {
                String lineSplit = line.replaceAll("\n"+"\t+", " ");
                String[] fragments = lineSplit.split(" ");
                strList = Arrays.asList(fragments);

                if (strList.size() == 0 || !strList.contains(previousPage)) {
                    Main.listFile.add(previousPage);
                } else {
                    if (strList.contains(previousPage) && !strList.contains(page)) {

                        int index = Main.listFile.indexOf(line);

                        int indexStr = line.indexOf(previousPage) + previousPage.length();
                        String str = line.substring(0, indexStr);
                        str = str + '\n';
                        for (int j = 0; j < tab; j++) {
                            str = str + '\t';
                        }

                        if (line.length() < str.length()) {
                            str = str + page;
                        } else {
                            str = str + page + line.substring(indexStr, line.length());
                        }

                        Main.listFile.set(index, str);
                    }
                }
            }
        }
    }
}
