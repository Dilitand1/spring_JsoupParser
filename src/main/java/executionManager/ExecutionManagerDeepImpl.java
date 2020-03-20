package executionManager;

import Context.Context;
import downloadLinks.DownloadLinks;
import pageDownloader.PageDownloader;
import pageObjects.PageObject;
import threading.DaemonMonitoring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class ExecutionManagerDeepImpl implements ExecutionManager {
    DownloadLinks downloadLinks;
    PageDownloader pageDownloader;

    Queue<String> linksQueue;
    Queue<PageObject> pageQueue;
    Logger logger;

    /**
     *
     * Реализация с глубокой прогрузкой со всеми вложениями.
     * Из за прокси работает очень медленно как решить хз. Можно распарсить поверхностно - это будет быстро
     *
     * Сначала тянет перечень всех линков потом заходит в каждый и распарсивает все поля (кроме телефона - нужно разбираться как выгрузить их)
     * @return
     * @throws InterruptedException
     */

    @Override
    public Context execute() throws InterruptedException {
        //Линки загружаются однопоточно. Сделаем многопоточность после того как допилю все остальное
        downloadLinks.downloadLinks();

        List<Future> f1 = new ArrayList<>();
        ExecutorService pageDownloaderService = Executors.newFixedThreadPool(5);
        //грузим контент со страниц, попробуем в 1 поток
        for (int i = 0; i < 1; i++) {
            f1.add(pageDownloaderService.submit(new Runnable() {
                @Override
                public void run() {
                    String url = null;
                    while ((url = linksQueue.poll()) != null) {
                        pageQueue.offer(pageDownloader.downloadPageContent(url));
                    }
                }
            }));
        }
        //сохраняем данные с страницы и картинки
        List<Future> f2 = new ArrayList<>();
        ExecutorService contentDownloaderService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 1; i++) {
            f2.add(contentDownloaderService.submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        PageObject po = null;
                        if ((po = pageQueue.poll()) != null) {
                            try {
                                System.out.println("Очередь есть работаем");
                                po.saveObject();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                if (linksQueue.isEmpty() && pageQueue.isEmpty()
                                        || f1.stream().map(x-> x.isDone() || x.isCancelled() ? 0 : 1).reduce(0,(x,y) -> x + y) == 0){
                                    break;
                                }
                                System.out.println("Очередь пуста спим 5 сек");
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }));
        }

        Thread thread = new Thread(new DaemonMonitoring(f1, f2, pageDownloaderService, contentDownloaderService, linksQueue));
        thread.setDaemon(true);
        thread.start();

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
