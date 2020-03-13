package JSOUP;

import org.jsoup.Jsoup;

public class JsoupFileWorker extends JsoupUrlWorker {
    public JsoupFileWorker(String url) {
        super(url);
    }

    @Override
    public void setDocument(String url)  {
        document = Jsoup.parse(url);
    }

}
