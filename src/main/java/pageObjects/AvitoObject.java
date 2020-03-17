package pageObjects;

public class AvitoObject extends PageObject {

    String title,price,adress,description,mainJpg;

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

    public void setMainJpg(String mainJpg) {
        this.mainJpg = mainJpg;
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

    public String getMainJpg() {
        return mainJpg;
    }
}
