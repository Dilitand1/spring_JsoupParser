package pageObjects;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class PageObject {
    static volatile AtomicInteger atomicInt = new AtomicInteger(0);
    int id;
    String ref;
    String title;
}
