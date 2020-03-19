package fileworker;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;

public class FileWorker {

    static String inputPath;

    String outputPath;
    String outputText;
    Boolean append;

    public static String readFile(String path) {
        String s = "";
        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            s = new String(bytes);
        } catch (IOException e) {
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

    public static void writeFile(String text, String path, boolean b) {
        try (FileOutputStream fos = new FileOutputStream(path, b)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(List<String> texts, String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
            for (String s : texts) {
                writeFile(s + "\n", path, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(InputStream in, String path) throws IOException {
        int ch;
        try (FileOutputStream fos = new FileOutputStream(path, false)) {
            while ((ch = in.read()) != -1) {
                fos.write(ch);
            }
            in.close();
        }
    }

    public static void writeFile(byte[] bytes, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path, false)) {
            for (int i = 0; i < bytes.length; i++) {
                fos.write(bytes[i]);
            }
        }
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
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
