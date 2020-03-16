package JSOUP;


import org.jsoup.nodes.Document;

public interface JsoupWorker {
    void setDocument(String url);

    Document getDocument();
    String getUrl();

    void downloadCountOfPages();
    default int getCountPages(/*String cssQ,String attr,String pattern*/){
        return 0;
    };

}
