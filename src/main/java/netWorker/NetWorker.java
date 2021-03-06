package netWorker;

import fileworker.FileWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.*;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetWorker {
    static public Queue<Proxy> proxyQueue;
    static public Logger logger;

    //url - откуда грузим, blockedMessage - сообщение о том что заблокировал firewall (везде разный может быть) , b - реагировать на эксепшены или нет
    public static Document downloadDocument(String url, String blockedMessage, boolean b) throws IOException, InterruptedException {
        while (proxyQueue.size() == 0) {
            logger.log(Level.INFO, "кончились прокси в очереди ждем когда вернется обратно");
            Thread.sleep(1000);
        }
        Proxy proxy = proxyQueue.poll(); //тянем проксю
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .proxy(proxy)
                    //.referrer("http://www.google.com")
                    .timeout(15 * 1000)
                    .get();
        } catch (IOException ioE) {
            if (ioE.getMessage().contains("Internal Server Error")) {
                logger.log(Level.WARNING, ioE.getMessage() + ". deleting proxy, taking next proxy");
                doc = downloadDocument(url, blockedMessage, b);
            } else if (ioE instanceof java.net.SocketTimeoutException
                    || ioE.getMessage().contains("Status=403")
                    || ioE.getMessage().contains("Server returned HTTP response code: 500")
                    || ioE.getMessage().contains("Connection reset")
                    || ioE.getMessage().contains("503 Too many open connections")
                    || ioE.getMessage().contains("Unexpected end of file from server")
                    || ioE.getMessage().contains("timed out")
                    || ioE.getMessage().contains("Status=404")
                    //|| ioE.getMessage().contains("HTTP error fetching URL. Status=403")
                    || ioE.getMessage().contains("Unable to tunnel through proxy")
                    || ioE.getMessage().contains("Connection refused: connect")) {
                logger.log(Level.INFO, proxy.address() + " " + ioE.getMessage() + ", taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            } else {
                proxyQueue.offer(proxy);
                System.out.println("throwing");
                throw ioE;
            }
        }
        if (doc.toString().contains(blockedMessage)) {
            logger.log(Level.INFO, "Proxy blocked by firewall changing proxy");
            proxyQueue.offer(proxy);
            doc = downloadDocument(url, blockedMessage, b);
        }
        proxyQueue.offer(proxy);
        return doc;
    }

    public static void writeUrlContentToFile(String urlContent, String pathToSave) throws IOException, InterruptedException {
        while (proxyQueue.size() == 0) {
            logger.log(Level.INFO, "кончились прокси в очереди ждем когда вернется обратно");
            Thread.sleep(1000);
        }
        //тянем прокси
        Proxy proxy = proxyQueue.poll();
        URL u;
        URLConnection c;
        try {
            u = new URL(urlContent);
            c = u.openConnection(proxy);
            c.setDoOutput(true);
            c.setDoInput(true);
            logger.log(Level.INFO, "downloading " + urlContent);
            BufferedInputStream BufferedInputStream = new BufferedInputStream(c.getInputStream());
            FileWorker.writeFile(BufferedInputStream, pathToSave);
            //возвращаем прокси назад
            proxyQueue.offer(proxy);
        } catch (IOException ioe) {
            if (ioe.getMessage().contains("Connection timed out")
                    || ioe.getMessage().contains("HTTP response code: 500")
                    || ioe.getMessage().contains("Connection refused: connect")
                    || ioe.getMessage().contains("Connection reset")
                    || ioe.getMessage().contains("HTTP response code: 503")
                    || ioe.getMessage().contains("Unexpected end of file from server")) {
                logger.log(Level.INFO, ioe.getMessage() + " , taking next proxy");
                proxyQueue.offer(proxy);
                writeUrlContentToFile(urlContent, pathToSave);
            } else {
                throw ioe;
            }
        }

    }

    public static Document readDocumentFromFile(String path){
        Document document = Jsoup.parse(FileWorker.readFile(path));
        return document;
    }

    public static void setLogger(Logger logger) {
        NetWorker.logger = logger;
    }

    public void setProxyQueue(Queue<Proxy> proxyQueue) {
        NetWorker.proxyQueue = proxyQueue;
    }

    public Queue<Proxy> getProxyQueue() {
        return proxyQueue;
    }

}