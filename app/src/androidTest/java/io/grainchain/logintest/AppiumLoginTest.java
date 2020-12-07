package io.grainchain.logintest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;


public class AppiumLoginTest {
    static final String APP = "C:/Users/jedua/appium-test/app/build/outputs/apk/debug/app-debug.apk";
    static final String APPIUM = "http://localhost:4724/wd/hub";

    public AndroidDriver driver;
    
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", APP);
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void login(){
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement user = wait.until
                (ExpectedConditions.presenceOfElementLocated
                        (MobileBy.AccessibilityId("username")));
        user.sendKeys("eduardo@hotmail.com");
        WebElement password = driver.findElement(MobileBy.AccessibilityId("password"));
        password.sendKeys("1234");
        WebElement button = driver.findElement(MobileBy.AccessibilityId("login"));
        //Assert button is disabled when password length is < 5
        assert(!button.isEnabled());

        password.clear();
        password.sendKeys("12345");
        //Assert button is enabled when password length is = 5
        assert (button.isEnabled());
        password.sendKeys("567890");
        //Assert button is enabled when password length is > 5
        assert (button.isEnabled());

        //Navigate to MainActivity Page
        button.click();
        WebElement textViewText = wait.until
                (ExpectedConditions.presenceOfElementLocated
                        (MobileBy.AccessibilityId("textview_first")));

        //Assert the username text is inside the TextView field
        assert(textViewText).getText().contains(user.getText());
    }
}
