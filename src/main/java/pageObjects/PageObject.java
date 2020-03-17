package pageObjects;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class PageObject {
    static volatile AtomicInteger atomicID = new AtomicInteger(0);
    int id;
    String ref;
    String title;

    static void setAtomicID(int id){
        atomicID.set(id);
    }

    public static int getAtomicId(){
        return atomicID.intValue();
    }
}
