package pageObjects;

public class AvitoObject extends PageObject {

    String titleClass = "title-info-title-text";
    String priceClass = "js-item-price";
    String adressClass = "item-address__string";
    String descriptionClass = "item-description-html";

    String title,price,adress,description;

    AvitoObject(){
       this.id = atomicInt.incrementAndGet();
    }

}
