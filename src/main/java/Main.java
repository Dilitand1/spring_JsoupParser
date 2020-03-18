
import JSOUP.JsoupFileWorker;
import JSOUP.JsoupWorker;
import executionManager.ExecutionManager;
import org.jsoup.nodes.Document;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pageObjects.PageObject;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {

    int id;
    JsoupWorker jsoupWorker;
    static Logger logger;
    Queue<PageObject> pageQueue;
    Queue<String> linksQueue;

    public static void main(String[] args) throws IOException, InterruptedException {

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main", Main.class);

        ExecutionManager executionManager = configurableApplicationContext.getBean("executionManager", ExecutionManager.class);
        executionManager.execute();

        JsoupFileWorker jsoupFileWorker = configurableApplicationContext.getBean("jsoupFileWorker2",JsoupFileWorker.class);


        //Document document = jsoupFileWorker.getDocument();
        //List<String> list = document.select("div.gallery-img-frame").stream().map(x -> "http:" + (x.attr("data-url"))).collect(Collectors.toList());
        //System.out.println(list);


        //executionManager.execute();


        //avitoDownloader.downloadContent();
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
}
