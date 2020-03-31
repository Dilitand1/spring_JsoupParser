package downloadLinks;

import org.jsoup.nodes.Document;

import java.net.Proxy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public abstract class DownloadLinks {

    Queue<String> linksQueue;
    Logger logger;

    public void downloadLinks(){};

    abstract void downloadCache();

    /**
     * @param url откуда грузим
     * @param m сообщение о том что заблокировал firewall (везде разный может быть)
     * @param b реагировать на эксепшены или нет
     * @return
     */
    abstract public Document downloadDocument(String url,String m, boolean b);

    abstract public Queue<String> getLinksQueue();

    abstract public int downloadCountOfPages();

}
