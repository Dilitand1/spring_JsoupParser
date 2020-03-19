package pageObjects;

import fileworker.FileWorker;
import netWorker.NetWorker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvitoObject extends PageObject {

    String price,adress,description;
    List<String> jpgFiles;

    @Override
    public String toString() {
        return super.toString() + "~" + price + "~" + adress + "~" + description + "~" + jpgFiles;
    }

    @Override
    public void saveObject() throws IOException {
        FileWorker.writeFile(this.toString(),outputPathContentInfo,true);
        for(int i = 0; i < jpgFiles.size();i++){
            NetWorker.writeUrlContentToFile(jpgFiles.get(i),(outputPathContent + "/" + getId() + "_" + i + ".jpg").replaceAll("\n","") + "\n");
        }
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setJpgFiles(List<String> jpgFiles) {
        this.jpgFiles = jpgFiles;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getAdress() {
        return adress;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getJpgFiles() {
        return jpgFiles;
    }

}
