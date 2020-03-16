package JSOUP;

import fileworker.FileWorker;
import org.jsoup.Jsoup;

public class JsoupFileWorker extends JsoupUrlWorker {

    public JsoupFileWorker() {

    }

    @Override
    public void setDocument(String url)  {
        this.document = Jsoup.parse(FileWorker.readFile(url));
    }

}
