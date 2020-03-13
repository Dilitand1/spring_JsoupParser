package JSOUP;


import org.jsoup.nodes.Document;

public interface JsoupWorker {
    void setDocument(String url);
    Document getDocument();
}
