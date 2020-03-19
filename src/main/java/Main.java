
import JSOUP.JsoupWorker;
import executionManager.ExecutionManager;
import fileworker.FileWorker;
import netWorker.NetWorker;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pageObjects.PageObject;
import proxy.ProxyFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Proxy;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;

public class Main {

    int id;
    JsoupWorker jsoupWorker;
    static Logger logger;
    Queue<PageObject> pageQueue;
    Queue<String> linksQueue;
    Queue<java.net.Proxy> proxyQueue;

    public static void main(String[] args) throws IOException, InterruptedException {

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main", Main.class);

        ExecutionManager executionManager = configurableApplicationContext.getBean("executionManager", ExecutionManager.class);
        executionManager.execute();

        //String s = "NTCN";
        //FileWorker.writeFile(new ByteArrayInputStream(s.getBytes()),"./outputPictures/1_0.jpg");

        //JsoupFileWorker jsoupFileWorker = configurableApplicationContext.getBean("jsoupFileWorker2",JsoupFileWorker.class);

    }

    void init() {
        id = Math.abs(new Random().nextInt());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setJsoupWorker(JsoupWorker jsoupWorker) {
        this.jsoupWorker = jsoupWorker;
    }

    public void setPageList(Queue pageQueue) {
        this.pageQueue = pageQueue;
    }

    public Queue<String> getLinksQueue() {
        return linksQueue;
    }

    public void setLinksQueue(Queue linksQueue) {
        this.linksQueue = linksQueue;
    }

    public void setPageQueue(Queue<PageObject> pageQueue) {
        this.pageQueue = pageQueue;
    }

    public Queue getPageQueue() {
        return pageQueue;
    }

    public Queue<Proxy> getProxyQueue() {
        return proxyQueue;
    }

    public void setProxyQueue(Queue<Proxy> proxyQueue) {
        this.proxyQueue = proxyQueue;
    }
}
