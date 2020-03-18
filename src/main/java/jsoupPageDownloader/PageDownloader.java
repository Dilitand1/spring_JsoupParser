package jsoupPageDownloader;

import org.jsoup.nodes.Document;
import pageObjects.PageObject;

public interface PageDownloader {
    void downloadContent();
    PageObject downloadPageContent(String url);
    void downloadLinks();
    Document downloadDocument(String url);
}
