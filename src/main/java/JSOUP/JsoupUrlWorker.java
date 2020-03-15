package JSOUP;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupUrlWorker implements JsoupWorker{
    String url;
    Document document;
    Elements elements;

    String cssQ, attr, pattern;

    public JsoupUrlWorker(){
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

    public void setUrl(String url){
        this.url = url;
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

    public void setCssQ(String cssQ) {
        this.cssQ = cssQ;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
