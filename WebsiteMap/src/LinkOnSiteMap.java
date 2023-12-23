import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class LinkOnSiteMap extends RecursiveAction {

    private ArrayList<String> linkListFromURL = new ArrayList<>();
    private String link;

    public LinkOnSiteMap(String link){
        this.link = link;
    }


    @Override
    protected void compute() {

        List<LinkOnSiteMap> taskList = new ArrayList<>();

        linkListFromURL = parsingWebPage(link);

        if (linkListFromURL.size() == 0 || linkListFromURL == null) {
            addLink(link, link);
        } else {
            for (String line : linkListFromURL) {

                line = urlCheck(line, link);
                if(!line.equals("")) {

                    line = addLink(link, line);
                    if (line != null && !line.equals(link)) {
                        LinkOnSiteMap task = new LinkOnSiteMap(line);
                        task.fork();
                        taskList.add(task);
                    }
                }
            }
        }


        for (LinkOnSiteMap task : taskList){
            task.join();
        }

    }


    public ArrayList<String> parsingWebPage(String URL) {

        ArrayList<String> list = new ArrayList<>();

        URL = URL.strip();
        if (URL.endsWith("/")) {
            Document doc = null;
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.getMessage();
            }

            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                e.getMessage();
            }

            if (doc != null) {
                Elements links = doc.select("a");
                links.forEach(link -> {
                    String line = link.attr("href");
                    list.add(line);
                });
            }
        }

        return list;
    }


    public String urlCheck(String line, String link){

//        1 - ""
//        2 - /
//        3 - /asxwdas/sacls...
//        4 - https://link...
//        5 - https://sfksdl/kdfjkdj....

        if(line.equals("")){
            return "";
        }

        if (line.charAt(0) == '/' && line.length() > 1) {
            line = Main.root.concat(line.substring(1));
        }

        if (line.startsWith(link)) {
            return line;
        }

        return "";
    }

    public String addLink(String URL, String link) {
        String str = URL + " " + link;
        if(!Main.listLink.contains(str)){
            Main.listLink.add(str);
            return link;
        }
        return null;
    }

}
