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
        Pattern pattern1 = Pattern.compile(pattern);
        Integer max = 0;
        Elements elements = document.select(cssQ/*"[data-marker*=page(]"*/);
        for(Element element : elements){
            String text = element.text();
            if (pattern1.matcher(text).matches()){
                if (max < Integer.parseInt(text))
                    max = Integer.parseInt(text);
            }
        }
        return max;
    }

    public void setCssQ(String cssQ) {
        this.cssQ = cssQ;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
