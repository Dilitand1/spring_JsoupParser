package fileworker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWorker {

    static String inputPath;

    String outputPath;
    String outputText;
    Boolean append;

    public static String readFile(String path) {
        String s = "";
        try(FileInputStream fis = new FileInputStream(path)){
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            s = new String(bytes);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return s;
    }

    public static String readFile(File file) {
        return readFile(file.getAbsolutePath());
    }

    public static String readFile() {
        return readFile(inputPath);
    }

    public static void writeFile(String text,String path,boolean b){
        try(FileOutputStream fos = new FileOutputStream(path,b)){
            fos.write(text.getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public void setAppend(Boolean append) {
        this.append = append;
    }
}
