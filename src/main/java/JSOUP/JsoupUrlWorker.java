package JSOUP;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupUrlWorker implements JsoupWorker{
    String url;
    Document document;
    Elements elements;

    public JsoupUrlWorker(String url){
        this.url = url;
    }

    public void setDocument(String url)  {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document = doc;
    }

    public void setElements(){
        elements = document.select("div#news__panel mix-tabber-slide2__panel");
    }

    public Document getDocument(){
        return this.document;
    }

    public String getUrl() {
        return url;
    }

    public Elements getElements() {
        return elements;
    }
}