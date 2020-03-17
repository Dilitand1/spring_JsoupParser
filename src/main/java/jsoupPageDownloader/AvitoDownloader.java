package jsoupPageDownloader;

import fileworker.FileWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pageObjects.AvitoObject;
import pageObjects.PageObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class AvitoDownloader implements PageDownloader {

    private Logger logger;

    private List<String> links = new ArrayList<>();
    private List<PageObject> pageObjects = new ArrayList<>();
    private String cachePath;
    private String linksPath;
    private String contentFolder;
    private Document documentLinks;
    private Map<Integer,String> cachedLinks = new TreeMap<>();

    private String priceClass;
    private String adressClass;
    private String descriptionClass;
    private String titleClass;
    private String pattern;
    private String cssPages;
    private String cssAllLinks;
    private String attr;
    private String firstPartref;
    private String url;
    private String urlLinkPageSuffix;


    public AvitoDownloader() {
    }

    @Override
    public void downloadContent() {
        downloadLinks();
        for (int i = 0; i < links.size(); i++) {
            logger.log(Level.INFO,"parsing " + links.get(i));
            AvitoObject avitoObject = new AvitoObject();
            Document document = downloadDocument(links.get(i));
            avitoObject.setPrice(document.select(priceClass).get(0).text());
            avitoObject.setTitle(document.select(titleClass).get(0).text());
            avitoObject.setAdress(document.select(adressClass).get(0).text());
            avitoObject.setDescription(document.select(descriptionClass).get(0).text());
            avitoObject.setMainJpg(document.select("img[src]").select("[src$=jpg]").attr("src"));
            downloadFile("http:" + avitoObject.getMainJpg(),contentFolder + "/" + AvitoObject.getAtomicId() + ".jpg");
            pageObjects.add(avitoObject);
        }
    }

    /**
     * Метод пробегается по страницам и выгружает все ссылки и сохраняет в файл
     */
    @Override
    public void downloadLinks() {
        findCache();//проверяем кэш
        if(links.size() == 0 && cachedLinks.size() == 0) { //проверяем что нет ни ссылок ни кэша
            logger.log(Level.INFO,"DownloadContent links");
            documentLinks = downloadDocument(url);
            int currentPage = 1;
            if (downloadCountOfPages() > 1) {
                while (true) {
                    documentLinks = downloadDocument(url + urlLinkPageSuffix + currentPage);
                    if (documentLinks == null) break;
                    Elements linkElements = documentLinks.select(cssAllLinks);
                    if (linkElements.size() > 0) {
                        for (Element e : linkElements) {
                            links.add(firstPartref + e.attr("href").toString());
                        }
                    } else {
                        break;
                    }
                    currentPage++;
                }
            } else {
                Elements linkElements = documentLinks.select(cssAllLinks);
                for (Element e : linkElements) {
                    links.add(firstPartref + e.attr("href").toString());
                }
            }
            logger.log(Level.INFO,"Saving links to file");
            FileWorker.writeFile(links,linksPath);
        }
    }

    public void downloadFile(String urlContent,String pathToSave){
        URL u;
        URLConnection c;
        try {
            u = new URL(urlContent);
            c = u.openConnection();
            c.setDoOutput(true);
            c.setDoInput(true);
            FileWorker.writeFile(new BufferedInputStream(c.getInputStream()),pathToSave);

        } catch (IOException e) {
            logger.log(Level.WARNING,urlContent);
            e.printStackTrace();
        }
    }
    public Document downloadDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    //.proxy(Proxy.NO_PROXY)
                    //.referrer("http://www.google.com")
                    .timeout(20 * 1000)
                    .get();
        } catch (IOException e) {
            logger.log(Level.WARNING,e.getMessage());
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

    public void findCache() {
        logger.log(Level.INFO,"Trying to find cache");
        System.out.println(FileWorker.fileExists(linksPath));
        if (FileWorker.fileExists(linksPath)) {
            links = Arrays.asList(FileWorker.readFile(linksPath).split("\n"));
            logger.log(Level.INFO,"Links found. Reading file links");
        }
        if (FileWorker.fileExists((cachePath))){
            logger.log(Level.INFO,"Cache found. Reading cache path");
            List<String> tmpList = Arrays.asList(FileWorker.readFile(cachePath).split("\n"));
            for(String s : tmpList){
                cachedLinks.put(Integer.parseInt(s.split("~")[0]),s.split("~")[1]);
            }
            logger.log(Level.INFO,"Deleting already downloaded files");
            links.removeAll(cachedLinks.values());
        }
    }

    public List<String> getLinks() {
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

    public void setContentFolder(String contentFolder) {
        this.contentFolder = contentFolder;
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

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public void setLinksPath(String linksPath) {
        this.linksPath = linksPath;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
