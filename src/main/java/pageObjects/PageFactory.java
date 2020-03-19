package pageObjects;

public class PageFactory {
    public static PageObject newAvitoObject(){
        PageObject pageObject = new AvitoObject();
        pageObject.setOutputPathContent("./outputPictures");;
        pageObject.setOutputPathContentInfo("outputContentInfo.txt");
        return pageObject;
    }
}
