package pageObjects;

import java.util.ArrayList;
import java.util.List;

public class AvitoObject extends PageObject {

    String title,price,adress,description;
    List jpgFiles = new ArrayList<String>();

    public AvitoObject(){
       this.id = atomicID.incrementAndGet();
    }

    public void setTitle(String title) {
        this.title = title;
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
