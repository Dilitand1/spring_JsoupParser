package fileworker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileWorker {

    public String readFile(String path) {
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

    public String readFile(File file) {
        return readFile(file.getAbsolutePath());
    }


    public void writeFile(String path,boolean b){

    }

}
