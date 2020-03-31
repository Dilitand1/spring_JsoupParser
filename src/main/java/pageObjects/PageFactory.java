package pageObjects;

public class PageFactory {

    //Надо внедрять параметры через спринг - потом допилю
    public static PageObject newPageObject(String type) {
        PageObject pageObject = null;
        switch (type) {
            case ("AvitoObject"):
                pageObject = new AvitoObject();
                pageObject.setOutputPathContent("./outputPictures");
                pageObject.setOutputPathContentInfo("outputContentInfo.txt");
                pageObject.setCachePath("cache2.txt");
                break;

            case ("SimpleAvitoObject"):
                pageObject = new SimpleAvitoObject();
                pageObject.setOutputPathContent("./outputPictures");
                pageObject.setOutputPathContentInfo("outputContentInfo.txt");
                pageObject.setCachePath("cache2.txt");
                break;
        }
        return pageObject;
    }
}
