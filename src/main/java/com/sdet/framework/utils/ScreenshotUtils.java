package com.sdet.framework.utils;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenshotUtils {

    public static byte[] captureScreenshot() {
        WebDriver driver = DriverFactory.getDriver();

        try {
            // Scrolls down the page automatically to capture full page
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000)) // 1000ms scroll delay
                    .takeScreenshot(driver);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            // Fallback to standard viewport screenshot on error
            return ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
        }
    }
}