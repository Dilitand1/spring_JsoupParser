package executionManager;

import downloadLinks.DownloadLinks;
import fileworker.FileWorker;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pageDownloader.PageDownloader;
import pageObjects.AvitoObject;
import pageObjects.PageObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;

public class ExecutionManagerDeepImplTest {

    private DownloadLinks downloadLinks;
    private PageDownloader pageDownloader;

    private Queue<String> linksQueue;
    private Queue<PageObject> pageQueue;

    @Before
    public void initBefore(){
        linksQueue = new ConcurrentLinkedQueue<>();
        pageQueue = new ConcurrentLinkedQueue<>();

        downloadLinks = Mockito.mock(DownloadLinks.class);
        pageDownloader = Mockito.mock(PageDownloader.class);
    }

    @Test
    public void execute() throws InterruptedException, IOException {
        linksQueue.add("www.yandex.ru");

        AvitoObject avitoObject = new AvitoObject();
        avitoObject.setDescription("desc");
        avitoObject.setAdress("address");
        avitoObject.setJpgFiles(new ArrayList<>());
        avitoObject.setPrice("1000");
        avitoObject.setOutputPathContentInfo("testTest.txt");

        Mockito.when(pageDownloader.downloadPageContent("www.yandex.ru")).thenReturn(avitoObject);

        ExecutionManagerDeepImpl executionManagerDeep = new ExecutionManagerDeepImpl();
        executionManagerDeep.setLinksQueue(linksQueue);
        executionManagerDeep.setDownloadLinks(downloadLinks);
        executionManagerDeep.setPageDownloader(pageDownloader);
        executionManagerDeep.setPageQueue(pageQueue);

        executionManagerDeep.execute();
        assertTrue(FileWorker.fileExists("testTest.txt"));
        Files.deleteIfExists(Paths.get("testTest.txt"));
    }
}