package pageObjects;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class PageObject {
    static volatile AtomicInteger atomicID = new AtomicInteger(0);
    private int id;
    private String ref;
    String title;
    String outputPathContent;
    String outputPathContentInfo;
    private String cachePath;

    public PageObject(){
        this.id = atomicID.incrementAndGet();
    }

    public static void setAtomicID(int id){
        atomicID.set(id);
    }

    public static int getAtomicId(){
        return atomicID.intValue();
    }

    @Override
    public String toString() {
        return id + "~" + ref + "~" + title;
    }

    abstract public void saveObject() throws IOException, InterruptedException;

    public void setOutputPathContent(String outputPathContent) {
        this.outputPathContent = outputPathContent;
    }

    public void setOutputPathContentInfo(String outputPathContentInfo) {
        this.outputPathContentInfo = outputPathContentInfo;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public String getCachePath() {
        return cachePath;
    }

    public void setCachePath(String cachePath) {
        this.cachePath = cachePath;
    }
}
