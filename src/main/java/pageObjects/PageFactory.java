package pageObjects;

public class PageFactory {

    //Надо внедрять параметры через спринг - потом допилю
    public static PageObject newAvitoObject(){
        PageObject pageObject = new AvitoObject();
        pageObject.setOutputPathContent("./outputPictures");;
        pageObject.setOutputPathContentInfo("outputContentInfo.txt");
        pageObject.setCachePath("cache2.txt");
        return pageObject;
    }
}
