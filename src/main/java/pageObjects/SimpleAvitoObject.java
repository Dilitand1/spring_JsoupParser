package pageObjects;

import fileworker.FileWorker;
import netWorker.NetWorker;

import java.io.IOException;
import java.util.List;

public class SimpleAvitoObject extends PageObject {

    private String price, address, description;
    private List<String> jpgFiles;

    @Override
    public String toString() {
        return super.toString() + "~" + price + "~" + address + "~" + description + "~" + jpgFiles;
    }

    @Override
    public void saveObject() throws IOException, InterruptedException {
        for (int i = 0; i < jpgFiles.size(); i++) {
            NetWorker.writeUrlContentToFile(jpgFiles.get(i), (outputPathContent + "/" + getId() + "_" + i + ".jpg"));
        }
        synchronized (SimpleAvitoObject.class) {
            //запись в общий файл будет синхронно чтобы не упасть
            FileWorker.writeFile((this.toString()).replaceAll("\n", " ") + "\n", outputPathContentInfo, true);
        }
        //допилить кэш
        //FileWorker.writeFile(this.getId() + "~" + this.getRef(),getCachePath(),true);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getJpgFiles() {
        return jpgFiles;
    }

    public void setJpgFiles(List<String> jpgFiles) {
        this.jpgFiles = jpgFiles;
    }
}
