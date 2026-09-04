package com.proton.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtils {

    public static String captureScreenshot() {

        return ((TakesScreenshot) DriverFactory.getDriver())
                .getScreenshotAs(OutputType.BASE64);
    }
}