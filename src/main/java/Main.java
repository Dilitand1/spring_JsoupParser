
import JSOUP.JsoupWorker;
import jsoupPageDownloader.AvitoDownloader;
import jsoupPageDownloader.PageDownloader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pageObjects.AvitoObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    int id;
    JsoupWorker jsoupWorker;

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main",Main.class);

        AvitoDownloader avitoDownloader = configurableApplicationContext.getBean("avitoDownloader", AvitoDownloader.class);
        //AvitoDownloader avitoDownloader = new AvitoDownloader();
        avitoDownloader.downloadLinks();
        List<String> arrayList = avitoDownloader.getLinks();
        for(String s : arrayList){
            System.out.println(s);
        }

        //JsoupWorker jsoupWorker = configurableApplicationContext.getBean("jsoupFileWorker",JsoupWorker.class);
        //ArrayList<String> arrayList = jsoupWorker.getLinks();
        //System.out.println(arrayList.get(0));

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

    public void setJsoupWorker(JsoupWorker jsoupWorker) {
        this.jsoupWorker = jsoupWorker;
    }
}
