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
    ExecutionManager executionManager;

    public static void main(String[] args) throws IOException, InterruptedException {

        /**
         *         Перед запуском незабудь обновить перечень прокси адресов (см. ProxyFactory)
         *         допилить индикацию что прокси заблочен файерволом и дать ему "остыть" (узнать время остывания)
         *         загружать линки многопоточно
         */

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main", Main.class);
        main.executionManager.execute();
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
