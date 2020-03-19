package downloadLinks;

import org.jsoup.nodes.Document;

import java.net.Proxy;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface DownloadLinks {

    void downloadLinks();

    void downloadCache();

    public Document downloadDocument(String url,String m, boolean b);

    public Queue<String> getLinksQueue();
}
