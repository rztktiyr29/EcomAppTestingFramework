package com.sdet.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScreenshotUtils {

    public static byte[] captureScreenshot() {

        TakesScreenshot ts =
                (TakesScreenshot) DriverFactory.getDriver();

        return ts.getScreenshotAs(OutputType.BYTES);
    }
}