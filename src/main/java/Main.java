import JSOUP.JsoupFileWorker;
import JSOUP.JsoupUrlWorker;
import JSOUP.JsoupWorker;
import fileworker.FileWorker;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    int id;

    JsoupWorker jsoupWorker;

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext configurableApplicationContext
                = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Main main = configurableApplicationContext.getBean("main",Main.class);

        JsoupWorker jsoupWorker = configurableApplicationContext.getBean("jsoupFileWorker",JsoupWorker.class);

        ArrayList<String> arrayList = jsoupWorker.getLinks();
        for (String s : arrayList){
            System.out.println(s);
        }
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
