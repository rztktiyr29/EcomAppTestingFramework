package com.proton.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import com.proton.utils.ExtentManager;
import com.proton.utils.ScreenshotUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {

        ExtentManager.getInstance();

        log.info(
                "Test Suite Execution Started: {}",
                context.getName()
        );
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest test =
                ExtentManager
                        .getInstance()
                        .createTest(
                                result.getTestClass()
                                        .getName()
                                        + " - "
                                        + result.getMethod()
                                        .getMethodName()
                        );

        ExtentManager.setTest(test);

        log.info(
                "Starting Test: {}",
                result.getMethod().getMethodName()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTest test =
                ExtentManager.getTest();

        if (test != null) {

            test.log(
                    Status.PASS,
                    "Test Passed Successfully"
            );
        }

        log.info(
                "Test Passed: {}",
                result.getMethod().getMethodName()
        );

        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        log.error(
                "Test Failed: "
                        + result.getMethod().getMethodName(),
                result.getThrowable()
        );

        ExtentTest test =
                ExtentManager.getTest();

        if (test != null) {

            if (result.getThrowable() != null) {

                test.log(
                        Status.FAIL,
                        result.getThrowable()
                );
            } else {

                test.log(
                        Status.FAIL,
                        "Test Failed"
                );
            }

            try {

                String screenshot =
                        ScreenshotUtils.captureScreenshot();

                ExtentManager.getTest()
                        .addScreenCaptureFromBase64String(
                                screenshot,
                                "Failure Screenshot"
                        );

            } catch (Exception e) {

                log.error(
                        "Unable to capture failure screenshot",
                        e
                );
            }
        }

        ExtentManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTest test =
                ExtentManager.getTest();

        if (test != null) {

            test.log(
                    Status.SKIP,
                    "Test skipped"
            );

            if (result.getThrowable() != null) {

                test.log(
                        Status.SKIP,
                        result.getThrowable()
                );
            }
        }

        ExtentManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {

        ExtentManager.flush();

        log.info(
                "Test Suite Finished."
        );
    }
}