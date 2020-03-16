package JSOUP;


import org.jsoup.nodes.Document;

import java.util.ArrayList;

public interface JsoupWorker {
    void setDocument(String url);

    Document getDocument();
    String getUrl();

    void downloadCountOfPages();
    void downloadLinks();

    int getCountPages(/*String cssQ,String attr,String pattern*/);
    ArrayList<String> getLinks();


}
