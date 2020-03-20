package threading;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DaemonMonitoring implements Runnable {

    List<Future> futureList1;
    List<Future> futureList2;
    ExecutorService e1;
    ExecutorService e2;
    Queue linksQueue;

    public DaemonMonitoring(List<Future> futureList1, List<Future> futureList2, ExecutorService e1, ExecutorService e2, Queue linksQueue) {
        this.futureList1 = futureList1;
        this.futureList2 = futureList2;
        this.e1 = e1;
        this.e2 = e2;
        this.linksQueue = linksQueue;
    }

    @Override
    public void run() {
        //Демон смотрит что все окончено - и вырубает все шедулеры, что в свою очередь заканчивает программу
        while (!linksQueue.isEmpty()
                && futureList1.stream().map(x -> x.isDone() ? 0 : 1).reduce(0,(x,y) -> x + y) != 0
                && futureList2.stream().map(x -> x.isDone() ? 0 : 1).reduce(0,(x,y) -> x + y) != 0
        ) {
            Thread.yield();
        }
        e1.shutdown();
        e2.shutdown();
    }
}