
import JSOUP.JsoupWorker;
import fileworker.FileWorker;
import jsoupPageDownloader.AvitoDownloader;
import logger.MyLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    int id;
    JsoupWorker jsoupWorker;
    static Logger logger;

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main",Main.class);

        //JsoupFileWorker jsoupFileWorker = configurableApplicationContext.getBean("jsoupFileWorker2",JsoupFileWorker.class);

        AvitoDownloader avitoDownloader = configurableApplicationContext.getBean("avitoDownloader", AvitoDownloader.class);
        avitoDownloader.downloadContent();


    }

    void init(){
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
}
