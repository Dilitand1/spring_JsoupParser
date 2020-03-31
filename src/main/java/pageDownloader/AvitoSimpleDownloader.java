package pageDownloader;

import fileworker.FileWorker;
import netWorker.NetWorker;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pageObjects.AvitoObject;
import pageObjects.PageFactory;
import pageObjects.PageObject;
import pageObjects.SimpleAvitoObject;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *Класс определеяет сначала количество страниц потом проходит по всем
 *
 */
public class AvitoSimpleDownloader implements PageDownloader {
    //Класс для быстрого парсинга всех страниц без входа внутрь.
    //Если нужно сделать быстро

    Logger logger;
    Queue<PageObject> pageQueue;

    @Override
    public PageObject downloadPageContent(String link) {
        Document document = downloadDocument(link,"блокировка123",true);
        Elements elements = document.select("div.item__line");
        //Перебираем все объявления создаем объект
        for (Element element : elements) {
            SimpleAvitoObject avitoObject = (SimpleAvitoObject) PageFactory.newPageObject("SimpleAvitoObject");
            //Ссылка
            avitoObject.setRef("https://www.avito.ru" + (element.select("a.snippet-link").attr("href")));
            //Название
            avitoObject.setTitle(element.select("a.snippet-link").text());
            //описание
            avitoObject.setDescription(
                    element.getElementsByTag("p").stream().map(x -> x.text().replaceAll("[~\n\t]", " ")).reduce("", (x, y) -> x + y));
            //цена
            avitoObject.setPrice(element.select("span.snippet-price").text());
            //address
            avitoObject.setAddress(element.getElementsByTag("meta").attr("content") + "_" + element.select("span.item-address-georeferences-item__content").text());
            //ссылки на картинки:
            Pattern pattern = Pattern.compile(" http.+? 1.5");
            List<String> listJpg = new ArrayList<>();
            for (Element s : element.getElementsByAttribute("srcset")) {
                Matcher matcher = pattern.matcher(s.toString());
                matcher.find();
                String ss = matcher.group();
                listJpg.add(ss.substring(1,ss.length()-4));
            }
            avitoObject.setJpgFiles(listJpg);
            pageQueue.add(avitoObject);
        }
        return null;
    }

    @Override
    public Document downloadDocument(String url, String blockMessage, boolean b) {
        Document doc = null;
        try {
            doc = NetWorker.downloadDocument(url,blockMessage,b);
        } catch (IOException | InterruptedException e) {
            logger.log(Level.WARNING, e.getMessage() + " link saved to failedLinks");
            FileWorker.writeFile(url + "\n", "failedLinks.txt", true);
            e.printStackTrace();
        }
        return doc;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Queue<PageObject> getPageQueue() {
        return pageQueue;
    }

    public void setPageQueue(Queue<PageObject> pageQueue) {
        this.pageQueue = pageQueue;
    }
}
