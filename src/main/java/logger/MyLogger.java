package logger;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MyLogger {

    private Logger logger;
    String configPath;

    MyLogger(Logger logger, String configPath){
        this.logger = logger;
        this.configPath = configPath;
    }

    void init() throws IOException {
        try(FileInputStream ins = new FileInputStream(configPath)){
            LogManager.getLogManager().readConfiguration(ins);
        } catch (FileNotFoundException fis){
            LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(loggerProperties().getBytes()));
        }
    }

    private static String loggerProperties(){
        String loggerProperties = "handlers = java.util.logging.FileHandler, java.util.logging.ConsoleHandler\n" +
                "java.util.logging.ConsoleHandler.level     = INFO\n" +
                "java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter";
                /*"java.util.logging.FileHandler.level     = INFO\n" +
                "java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter\n" +
                "java.util.logging.FileHandler.append    = true\n" +
                "java.util.logging.FileHandler.pattern   = log.%u.%g.txt\n" +*/

        return loggerProperties;
    }

    public Logger getLogger() {
        return logger;
    }
}
