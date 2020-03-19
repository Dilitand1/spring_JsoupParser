package netWorker;

import fileworker.FileWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetWorker {
    static public Queue<Proxy> proxyQueue;
    static public Logger logger;

    public static Document downloadDocument(String url, String blockedMessage, boolean b) throws IOException, InterruptedException {
        while (proxyQueue.size() == 0) {
            logger.log(Level.INFO,"кончились прокси в очереди ждем когда вернется обратно");
            Thread.sleep(1000);
        }
        Proxy proxy = proxyQueue.poll(); //тянем проксю
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .proxy(proxy)
                    //.referrer("http://www.google.com")
                    .timeout(30 * 1000)
                    .get();
        } catch (ConnectException ce) {
            if (ce.getMessage().contains("Connection timed out")) {
                logger.log(Level.INFO, "Time out connection, taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            } else {
                ce.printStackTrace();
            }
        }
        if (doc.toString().contains(blockedMessage)) {
            logger.log(Level.INFO, "Proxy blocked changing proxy");
            proxyQueue.offer(proxy);
            doc = downloadDocument(url, blockedMessage, b);
        }
        proxyQueue.offer(proxy);
        return doc;
    }

    public static void writeUrlContentToFile(String urlContent, String pathToSave) throws IOException {
        Proxy proxy = proxyQueue.poll();
        URL u;
        URLConnection c;
        try {
            u = new URL(urlContent);
            c = u.openConnection(proxy);
            c.setDoOutput(true);
            c.setDoInput(true);
            logger.log(Level.INFO, "downloading " + urlContent);
            FileWorker.writeFile(new BufferedInputStream(c.getInputStream()), pathToSave);
        } catch (ConnectException ce) {
            if (ce.getMessage().contains("Connection timed out")) {
                logger.log(Level.INFO, "Time out connection, taking next proxy");
                proxyQueue.offer(proxy);
                writeUrlContentToFile(urlContent, pathToSave);
            } else {
                ce.printStackTrace();
            }
        }
    }

    public static void setLogger(Logger logger) {
        NetWorker.logger = logger;
    }

    public void setProxyQueue(Queue<Proxy> proxyQueue) {
        NetWorker.proxyQueue = proxyQueue;
    }

}
