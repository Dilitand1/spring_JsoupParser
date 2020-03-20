package netWorker;

import fileworker.FileWorker;
import org.jsoup.HttpStatusException;
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
                    .timeout(30 * 1000)
                    .get();
        } catch (SocketTimeoutException stE) {
            if (stE.getMessage().contains("Read timed out")) {
                logger.log(Level.INFO, stE.getMessage() + ", taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            }
            else {
                throw stE;
            }
        } catch (ConnectException cE) {
            if (cE.getMessage().contains("Connection timed out")) {
                logger.log(Level.INFO, "Time out connection, taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            } else if (cE.getMessage().contains("Status=404") || cE.getMessage().contains("Status=403") || cE.getMessage().contains("Connection refused: connect")) {
                logger.log(Level.INFO, cE.getMessage() + ", taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            } else {
                cE.printStackTrace();
            }
        } catch (SocketException seE) {
            if (seE.getMessage().contains("Unexpected end of file from server")) {
                logger.log(Level.INFO, seE.getMessage() + ", taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            }
            else {
                throw seE;
            }
        } catch (IOException ioE) {
            if (ioE.getMessage().contains("Internal Server Error")) {
                logger.log(Level.WARNING, ioE.getMessage() + ". deleting proxy, taking next proxy");
                doc = downloadDocument(url, blockedMessage, b);
            } else if (ioE.getMessage().contains("Status=403") || ioE.getMessage().contains("503 Too many open connections"))  {
                logger.log(Level.INFO, ioE.getMessage() + ", taking next proxy");
                proxyQueue.offer(proxy);
                doc = downloadDocument(url, blockedMessage, b);
            }
            else {
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
            BufferedInputStream BufferedInputStream = new BufferedInputStream(c.getInputStream());
            FileWorker.writeFile(BufferedInputStream, pathToSave);
        } catch (ConnectException ce) {
            if (ce.getMessage().contains("Connection timed out") || ce.getMessage().contains("Connection refused: connect")) {
                logger.log(Level.INFO, "Time out connection, taking next proxy");
                proxyQueue.offer(proxy);
                writeUrlContentToFile(urlContent, pathToSave);
            } else {
                System.out.println("writeUrlContentToFile" + ce.getMessage());
                ce.printStackTrace();
            }
        } catch (IOException ioe) {
            if (ioe.getMessage().contains("HTTP response code: 503") || ioe.getMessage().contains("Unexpected end of file from server")) {
                logger.log(Level.INFO, ioe.getMessage() + " , taking next proxy");
                proxyQueue.offer(proxy);
                writeUrlContentToFile(urlContent, pathToSave);
            } else {
                throw ioe;
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