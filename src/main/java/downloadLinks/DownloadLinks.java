package downloadLinks;

import org.jsoup.nodes.Document;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface DownloadLinks {

    void downloadLinks();

    Document downloadDocument(String url);

    void downloadCache();

    public Queue<String> getLinksQueue();
}
