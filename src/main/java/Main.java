import executionManager.ExecutionManager;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pageObjects.PageObject;
import java.io.IOException;
import java.net.Proxy;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;

public class Main {

    int id;
    static Logger logger;
    Queue<PageObject> pageQueue;
    Queue<String> linksQueue;
    Queue<java.net.Proxy> proxyQueue;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         *         Перед запуском незабудь обновить перечень прокси адресов (см. ProxyFactory)
         *
         *         допилить кэш
         *         proxyfactory - реализовать внедрение параметров через спринг
         *         допилить индикацию что прокси заблочен файерволом и дать ему "остыть" (узнать время остывания)
         *         загружать линки многопоточно
         *         удалить ненужные классы (JSOUP package)
         */

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main", Main.class);

        ExecutionManager executionManager = configurableApplicationContext.getBean("executionDeepManager", ExecutionManager.class);
        executionManager.execute();
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
