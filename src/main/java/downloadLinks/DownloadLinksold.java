package downloadLinks;

import org.jsoup.nodes.Document;

import java.util.Queue;

public interface DownloadLinksold {

    void downloadLinks();

    void downloadCache();

    /**
     * @param url откуда грузим
     * @param m сообщение о том что заблокировал firewall (везде разный может быть)
     * @param b реагировать на эксепшены или нет
     * @return
     */
    public Document downloadDocument(String url, String m, boolean b);

    public Queue<String> getLinksQueue();

    public int downloadCountOfPages();
}
