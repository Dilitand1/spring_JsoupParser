package executionManager;

import Context.Context;
import downloadLinks.DownloadLinks;
import pageDownloader.PageDownloader;
import pageObjects.PageObject;

import java.io.IOException;
import java.util.Queue;
import java.util.logging.Logger;

public class ExecutionManagerImpl implements ExecutionManager {
    DownloadLinks downloadLinks;
    PageDownloader pageDownloader;

    Queue<String> linksQueue;
    Queue<PageObject> pageQueue;
    Logger logger;

    @Override
    public Context execute() throws InterruptedException {
        downloadLinks.downloadLinks();

        //грузим контент со страниц
        String url = null;
        while ((url = linksQueue.poll()) != null) {
            pageQueue.offer(pageDownloader.downloadPageContent(url));
        }

        //сохраняем данныес страницы и картинки
        PageObject po = null;
        while ((po = pageQueue.poll()) != null){
            try {
                po.saveObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setDownloadLinks(DownloadLinks downloadLinks) {
        this.downloadLinks = downloadLinks;
    }

    public void setPageDownloader(PageDownloader pageDownloader) {
        this.pageDownloader = pageDownloader;
    }

    public void setLinksQueue(Queue<String> linksQueue) {
        this.linksQueue = linksQueue;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setPageQueue(Queue<PageObject> pageQueue) {
        this.pageQueue = pageQueue;
    }
}
