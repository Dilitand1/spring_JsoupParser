package logger;

import java.util.logging.Logger;

public class MyLogger {

    private volatile static Logger instance;
    private MyLogger(){};

    public Logger getInstance(){
        if (instance == null){
            synchronized (MyLogger.class){
                if (instance == null){
                    instance = Logger.getLogger(MyLogger.class.getName());

                }
            }
        }
        return instance;
    }

    private static void setLoggerProperties(){
        String prop = "handlers = java.util.logging.ConsoleHandler";

    }

}
