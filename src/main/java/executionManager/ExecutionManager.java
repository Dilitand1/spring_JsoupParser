package executionManager;

import Context.Context;

public interface ExecutionManager {

    Context execute() throws InterruptedException;
}
