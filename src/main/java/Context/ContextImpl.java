package Context;

import exception.ExeptionHandler;

import java.util.List;

/**
 *
 * Не реализован
 */

public class ContextImpl implements Context {
    private final List<Thread> threadList;
    private ExeptionHandler exeptionHandler;

    public ContextImpl(List<Thread> threadList, ExeptionHandler exeptionHandler) {
        this.threadList = threadList;
        this.exeptionHandler = exeptionHandler;
    }

    @Override
    public int getCompletedTaskCount() {
        int counter = 0;
        for (Thread thread : threadList) {
            counter += thread.isAlive() ? 0 : 1;
        }
        return counter - this.getFailedTaskCount() - this.getInterruptedTaskCount();
    }

    @Override
    public int getFailedTaskCount() {
        return exeptionHandler.getCountFailed();
    }

    @Override
    public int getInterruptedTaskCount() {
        return exeptionHandler.getCountInterrupted();
    }

    @Override
    public void interrupt() {
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).interrupt();
        }
    }

    @Override
    public boolean isFinished() {
        return getCompletedTaskCount() == threadList.size() - this.getFailedTaskCount() - this.getInterruptedTaskCount();
    }
}
