package jsoupPageDownloader;

import org.jsoup.nodes.Document;
import pageObjects.PageObject;

public interface PageDownloader {

    PageObject downloadPageContent(String url);
    void downloadLinks();
    Document downloadDocument(String url,String blockMessage, boolean b);
}
