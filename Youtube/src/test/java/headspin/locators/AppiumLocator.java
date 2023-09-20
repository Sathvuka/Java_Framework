package headspin.locators;

import org.openqa.selenium.By;

public class AppiumLocator {

    public static By youtubelogo= By.xpath("//android.widget.ImageView[@content-desc='YouTube']");

    public static By searchicon= By.xpath("//android.widget.ImageView[@content-desc='Search']");

    public static By searchtab=By.xpath("//android.widget.EditText[contains(@text,'Search')]");

    public static By searchvideos=By.xpath("(//android.widget.TextView[contains(@text,'headspin')])[1]");

    public static By playvideo =By.xpath("(//android.view.ViewGroup)[5]");

    public static By ad=By.xpath("//android.widget.ImageView[@content-desc='Close ad panel']");

    public static By videoplayer=By.xpath("//android.widget.FrameLayout[@content-desc='Video player']");

    public static By sharebutton=By.xpath("//android.view.ViewGroup[@content-desc='Share']");


}
