package JSOUP;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class JsoupUrlWorker implements JsoupWorker{
    String url;
    Document document;

    private Elements linkElements;
    ArrayList<String> links = new ArrayList<>();

    int countOfPages;

    private String firstPartRef,cssQ, attr, pattern;

    JsoupUrlWorker(){
    }

    void init(){
        downloadCountOfPages();
        downloadLinks();
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
    public void setLinkElements(String cssQ){
        this.linkElements = document.select(cssQ);
    }

    @Override
    public void downloadCountOfPages() {
        Pattern pattern1 = Pattern.compile(pattern);
        Integer max = 0;
        Elements elements = document.select(cssQ/*"[data-marker*=page(]"*/);
        for(Element element : elements){
            String text = element.text();
            if (pattern1.matcher(text).matches()){
                if (max < Integer.parseInt(text))
                    max = Integer.parseInt(text);
            }
        }
        countOfPages = max;
    }

    public void downloadLinks() {
        for(Element e : linkElements){
            links.add(getFirstPartRef() + e.attr("href").toString());
        }
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

    public ArrayList<String> getLinks() {
        return links;
    }

    public String getFirstPartRef() {
        return firstPartRef;
    }

    public Elements getLinkElements() {
        return new Elements(linkElements);
    }

    @Override
    public int getCountPages(/*String cssQ,String attr,String pattern*/){
        return countOfPages;
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

    public void setFirstPartRef(String firstPartRef) {
        this.firstPartRef = firstPartRef;
    }
}
