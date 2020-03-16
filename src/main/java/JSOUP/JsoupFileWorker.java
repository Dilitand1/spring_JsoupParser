package JSOUP;

import fileworker.FileWorker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

public class JsoupFileWorker extends JsoupUrlWorker {


    public JsoupFileWorker() {

    }

    @Override
    public void setDocument(String url)  {
        this.document = Jsoup.parse(FileWorker.readFile(url));
    }

    @Override
    public int getCountPages(/*String cssQ,String attr,String pattern*/){
        return countOfPages;
    }

}
