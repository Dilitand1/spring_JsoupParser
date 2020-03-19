package jsoupPageDownloader;

import fileworker.FileWorker;
import netWorker.NetWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pageObjects.AvitoObject;
import pageObjects.PageFactory;
import pageObjects.PageObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AvitoDownloader2 implements PageDownloader {

    Queue<String> linksQueue;
    Queue<PageObject> pageList;
    Logger logger;

    String priceClass, titleClass, adressClass, descriptionClass;
    String picture_css, picture_attr, contentFolder;

    @Override
    public PageObject downloadPageContent(String link) {
        AvitoObject avitoObject = (AvitoObject) PageFactory.newAvitoObject();
        logger.log(Level.INFO, "parsing " + link);
        Document document = downloadDocument(link,"блокировка123",true);
        avitoObject.setRef("link");
        avitoObject.setPrice(document.select(priceClass).get(0).text());
        avitoObject.setTitle(document.select(titleClass).get(0).text());
        avitoObject.setAdress(document.select(adressClass).get(0).text());
        avitoObject.setDescription(document.select(descriptionClass).get(0).text());
        List<String> list = document.select(picture_css).stream().map(x -> "http:" + (x.attr(picture_attr))).collect(Collectors.toList());
        avitoObject.setJpgFiles(list);
        return avitoObject;
    }

    @Override
    public void downloadLinks() {
        return;
    }

    @Override
    public Document downloadDocument(String url,String blockMessage, boolean b) {

        Document doc = null;

        try {
            doc = NetWorker.downloadDocument(url,blockMessage,b);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage() + " link saved to failedLinks");
            FileWorker.writeFile(url, "failedLinks.txt", true);
            e.printStackTrace();
        }
        return doc;
    }

    public void setLinksQueue(Queue<String> linksQueue) {
        this.linksQueue = linksQueue;
    }

    public void setPageList(Queue<PageObject> pageList) {
        this.pageList = pageList;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setPriceClass(String priceClass) {
        this.priceClass = priceClass;
    }

    public void setTitleClass(String titleClass) {
        this.titleClass = titleClass;
    }

    public void setAdressClass(String adressClass) {
        this.adressClass = adressClass;
    }

    public void setDescriptionClass(String descriptionClass) {
        this.descriptionClass = descriptionClass;
    }

    public void setPicture_css(String picture_css) {
        this.picture_css = picture_css;
    }

    public void setPicture_attr(String picture_attr) {
        this.picture_attr = picture_attr;
    }

    public void setContentFolder(String contentFolder) {
        this.contentFolder = contentFolder;
    }
}
