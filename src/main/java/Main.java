
import JSOUP.JsoupFileWorker;
import JSOUP.JsoupWorker;
import fileworker.FileWorker;
import jsoupPageDownloader.AvitoDownloader;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Random;

public class Main {

    int id;
    JsoupWorker jsoupWorker;

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main",Main.class);

        JsoupFileWorker jsoupFileWorker = configurableApplicationContext.getBean("jsoupFileWorker2",JsoupFileWorker.class);
        AvitoDownloader avitoDownloader = configurableApplicationContext.getBean("avitoDownloader", AvitoDownloader.class);
        //AvitoDownloader avitoDownloader = new AvitoDownloader();

        avitoDownloader.downloadContent();

        //avitoDownloader.downloadContent("http://48.img.avito.st/640x480/5055920348.jpg","./outputPictures/1.jpg");

        /*
        avitoDownloader.downloadLinks();
        List<String> arrayList = avitoDownloader.getLinks();
        for(String s : arrayList){
            System.out.println(s);
        }

         */
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
