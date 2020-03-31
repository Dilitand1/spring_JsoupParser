package downloadLinks;

import netWorker.NetWorker;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class DownloadSimpleAvitoLinks extends DownloadLinks {

    private Queue<String> linksQueue;

    private String url;

    private String cssPages;
    private String patternCountOfPages;
    private String urlLinkPageSuffix;
    private int countOfPages;

    private Logger logger;

    @Override
    public void downloadLinks() {
        downloadCountOfPages();
        for (int i = 1; i <= countOfPages;i++){
            linksQueue.offer(url + urlLinkPageSuffix + i);
        }
    }

    @Override
    public void downloadCache() {
        return;
        //кэш потом
    }

    //Грузим документ для парсинга
    @Override
    public Document downloadDocument(String url, String message, boolean b) {
        Document doc = null;
        try {
            doc = NetWorker.downloadDocument(url,message,b);
        } catch (IOException | InterruptedException e) {
            if (b) {
                e.printStackTrace();
            }
        }
        return doc;
    }

    @Override
    //Определяем количество страниц
    public int downloadCountOfPages() {
        logger.log(Level.INFO,"Загружаем количество страниц");
        Pattern pattern1 = Pattern.compile(patternCountOfPages);
        Integer max = 0;
        Elements elements = downloadDocument(url,"firewall",true).select(cssPages);
        for (Element element : elements) {
            String text = element.text();
            if (pattern1.matcher(text).matches()) {
                if (max < Integer.parseInt(text))
                    max = Integer.parseInt(text);
            }
        }
        countOfPages = max;
        logger.log(Level.INFO, String.format("Всего %d страниц",max));
        return max;
    }

    @Override
    public Queue<String> getLinksQueue() {
        return linksQueue;
    }

    public void setLinksQueue(Queue<String> linksQueue) {
        this.linksQueue = linksQueue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCssPages() {
        return cssPages;
    }

    public void setCssPages(String cssPages) {
        this.cssPages = cssPages;
    }

    public String getPatternCountOfPages() {
        return patternCountOfPages;
    }

    public void setPatternCountOfPages(String patternCountOfPages) {
        this.patternCountOfPages = patternCountOfPages;
    }

    public String getUrlLinkPageSuffix() {
        return urlLinkPageSuffix;
    }

    public void setUrlLinkPageSuffix(String urlLinkPageSuffix) {
        this.urlLinkPageSuffix = urlLinkPageSuffix;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

}
