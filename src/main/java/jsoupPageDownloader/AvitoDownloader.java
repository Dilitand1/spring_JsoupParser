package jsoupPageDownloader;

import JSOUP.JsoupWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pageObjects.PageObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AvitoDownloader implements PageDownloader {

    private List<String> pages;
    private List<String> ipList;
    private List<String> links  = new ArrayList<>();;
    private PageObject pageObject;
    private String cachePath;
    private Document documentLinks;
    private Document document;
    private int countOfPages;

    private String priceClass ;
    private String adressClass ;
    private String descriptionClass ;
    private String titleClass ;
    private String pattern ;
    private String cssPages ;
    private String cssAllLinks;
    private String attr;
    private String firstPartref;
    private String url;
    private String urlLinkPageSuffix;

    public AvitoDownloader() {
    }

    @Override
    public void downloadContent() {

    }

    /**
     * Метод пробегается по страницам и выгружает все ссылки
     */
    @Override
    public void downloadLinks() {
        documentLinks = downloadDocument(url);
        int currentPage = 1;
        if (downloadCountOfPages() > 1){
            while(true){
                documentLinks = downloadDocument(url + urlLinkPageSuffix + currentPage);
                if (documentLinks == null) break;
                Elements linkElements = documentLinks.select(cssAllLinks);
                if (linkElements.size() > 0) {
                    for (Element e : linkElements) {
                        links.add(firstPartref + e.attr("href").toString());
                    }
                }
                else {
                    break;
                }
                currentPage++;
            }
        }
        else {
            Elements linkElements = documentLinks.select(cssAllLinks);
            for (Element e : linkElements) {
                links.add(firstPartref + e.attr("href").toString());
            }
        }
    }

    public Document downloadDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    //.userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .proxy("82.194.235.142",8080)
                    //.referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            doc = null;
        }
        return doc;
    }

    public int downloadCountOfPages() {
        Pattern pattern1 = Pattern.compile(pattern);
        Integer max = 0;
        Elements elements = documentLinks.select(cssPages);
        for (Element element : elements) {
            String text = element.text();
            if (pattern1.matcher(text).matches()) {
                if (max < Integer.parseInt(text))
                    max = Integer.parseInt(text);
            }
        }
        return max;
    }

    public List<String> getLinks(){
        return links;
    }

    public void setPriceClass(String priceClass) {
        this.priceClass = priceClass;
    }

    public void setAdressClass(String adressClass) {
        this.adressClass = adressClass;
    }

    public void setDescriptionClass(String descriptionClass) {
        this.descriptionClass = descriptionClass;
    }

    public void setTitleClass(String titleClass) {
        this.titleClass = titleClass;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setCssPages(String cssPages) {
        this.cssPages = cssPages;
    }

    public void setCssAllLinks(String cssAllLinks) {
        this.cssAllLinks = cssAllLinks;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public void setFirstPartref(String firstPartref) {
        this.firstPartref = firstPartref;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlLinkPageSuffix(String urlLinkPageSuffix) {
        this.urlLinkPageSuffix = urlLinkPageSuffix;
    }

    //    public void setLinkDocument(String url) {
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(url)
//                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
//                    //.proxy(Proxy.NO_PROXY)
//                    .referrer("http://www.google.com")
//                    .get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        documentLinks = doc;
//    }
}
