package com.sdet.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public final class ExtentManager {

    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {

        if (extent == null) {

            File reportDirectory =
                    new File("reports");

            if (!reportDirectory.exists()) {
                reportDirectory.mkdirs();
            }

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            "reports/ExtentReport.html"
                    );

            spark.config().setReportName(
                    "E Test Execution Report"
            );

            spark.config().setDocumentTitle(
                    "Automation Results"
            );

            spark.config().setTheme(
                    Theme.DARK
            );

            extent = new ExtentReports();

            extent.attachReporter(spark);

            extent.setSystemInfo(
                    "Framework",
                    "Selenium 4 + TestNG"
            );

            extent.setSystemInfo(
                    "Application",
                    "Proton Mail Web"
            );
        }

        return extent;
    }

    public static ExtentTest getTest() {

        return extentTest.get();
    }

    public static void setTest(ExtentTest test) {

        extentTest.set(test);
    }

    public static void unload() {

        extentTest.remove();
    }

    public static synchronized void flush() {

        if (extent != null) {
            extent.flush();
        }
    }
}