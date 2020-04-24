import downloadLinks.DownloadSimpleAvitoLinks;
import executionManager.ExecutionManager;
import netWorker.NetWorker;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    int id;
    static Logger logger;
    ExecutionManager executionManager;
    List list;

    public void setList(List list) {
        this.list = list;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         *         Перед запуском незабудь обновить перечень прокси адресов (см. ProxyFactory)
         *         нужно будет допилить индикацию что прокси заблочен файерволом и дать ему "остыть" (узнать время остывания)
         *         загружать линки многопоточно (хотя и так норм работает)
         *
         *         !!!Не делай больше через xml...
         */

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        //Main main = configurableApplicationContext.getBean("main", Main.class);

        Main main = configurableApplicationContext.getBean("mainSimpleAvitoLinks",Main.class);
        main.executionManager.execute();
        //test();
    }

    public static void test(){
        Document document = NetWorker.readDocumentFromFile("test222.html");
        Elements elements = document.select("div.item__line");
        System.out.println(elements.get(0));

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

    public void setExecutionManager(ExecutionManager executionManager) {
        this.executionManager = executionManager;
    }
}
