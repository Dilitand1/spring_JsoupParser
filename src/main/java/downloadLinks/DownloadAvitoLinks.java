package downloadLinks;

import fileworker.FileWorker;
import netWorker.NetWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Proxy;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DownloadAvitoLinks implements DownloadLinks {

    Queue<String> linksQueue;
    Logger logger;

    String linksPath;
    String cachePath;
    Map<Integer, String> cachedLinks;

    String url;
    String cssPages;
    String patternCountOfPages;
    String urlLinkPageSuffix;
    String cssAllLinks;
    String firstPartref;

    int currentPage;
    int countOfPages;
    Document document;

    @Override
    public void downloadLinks() {
        downloadCache();//проверяем кэш
        if (linksQueue.size() == 0 && cachedLinks.size() == 0) { //проверяем что нет ни ссылок ни кэша
            logger.log(Level.INFO, "Download links");
            document = downloadDocument(url,"заблокирован",true);
            currentPage = 0;
            if (downloadCountOfPages() > 1) {
                while (true) {
                    logger.log(Level.INFO, "Текущая страница " + ++currentPage);
                    document = downloadDocument(url + urlLinkPageSuffix + currentPage,"заблокирован", currentPage > countOfPages);
                    if (document == null) break;
                    Elements linkElements = document.select(cssAllLinks);
                    if (linkElements.size() > 0) {
                        for (Element e : linkElements) {
                            linksQueue.offer(firstPartref + e.attr("href").toString());
                        }
                    } else {
                        break;
                    }
                }
            } else {
                Elements linkElements = document.select(cssAllLinks);
                for (Element e : linkElements) {
                    linksQueue.add(firstPartref + e.attr("href").toString());
                }
            }
            logger.log(Level.INFO, "Saving links to file");
            FileWorker.writeFile(new ArrayList<>(linksQueue), linksPath);
        }
    }

    @Override
    public Document downloadDocument(String url,String m, boolean b) {
        Document doc = null;
        try {
            doc = NetWorker.downloadDocument(url,m,b);
        } catch (IOException e) {
            if (b) {
                e.printStackTrace();
            }
        }
        return doc;
    }

    @Override
    public void downloadCache() {
        logger.log(Level.INFO, "Trying to find cache");
        //System.out.println(FileWorker.fileExists(linksPath));
        if (FileWorker.fileExists(linksPath)) {
            logger.log(Level.INFO, "Links found. Reading file links");
            List<String> links = Arrays.asList(FileWorker.readFile(linksPath).split("\n"));
            for (String s : links) {
                linksQueue.offer(s);
            }
        }
        if (FileWorker.fileExists((cachePath))) {
            logger.log(Level.INFO, "Cache found. Reading cache path");
            List<String> tmpList = Arrays.asList(FileWorker.readFile(cachePath).split("\n"));
            for (String s : tmpList) {
                cachedLinks.put(Integer.parseInt(s.split("~")[0]), s.split("~")[1]);
            }
            logger.log(Level.INFO, "Deleting already downloaded files");
            linksQueue.removeAll(cachedLinks.values());
        }
    }

    public int downloadCountOfPages() {
        Pattern pattern1 = Pattern.compile(patternCountOfPages);
        Integer max = 0;
        Elements elements = document.select(cssPages);
        for (Element element : elements) {
            String text = element.text();
            if (pattern1.matcher(text).matches()) {
                if (max < Integer.parseInt(text))
                    max = Integer.parseInt(text);
            }
        }
        countOfPages = max;
        return max;
    }

    public Queue<String> getLinksQueue() {
        return linksQueue;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setLinksPath(String linksPath) {
        this.linksPath = linksPath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }

    public void setCachedLinks(Map<Integer, String> cachedLinks) {
        this.cachedLinks = cachedLinks;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCssPages(String cssPages) {
        this.cssPages = cssPages;
    }

    public void setPatternCountOfPages(String patternCountOfPages) {
        this.patternCountOfPages = patternCountOfPages;
    }

    public void setUrlLinkPageSuffix(String urlLinkPageSuffix) {
        this.urlLinkPageSuffix = urlLinkPageSuffix;
    }

    public void setCssAllLinks(String cssAllLinks) {
        this.cssAllLinks = cssAllLinks;
    }

    public void setFirstPartref(String firstPartref) {
        this.firstPartref = firstPartref;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void setLinksQueue(Queue<String> linksQueue) {
        this.linksQueue = linksQueue;
    }
}
