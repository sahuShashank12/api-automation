package com.mytaxi.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ExtReports extends TestListenerAdapter {

    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @Override
    public void onStart(ITestContext testContext) {

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/Reports/testReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("API Testing Report");
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Shashank");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test=extent.createTest(result.getName());
        test.log(Status.PASS,"TestCase Passed :"+result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.log(Status.FAIL, "TestCase Failed : "+result.getName());
        test.log(Status.FAIL, "TestCase Failed : "+result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test=extent.createTest(result.getName());
        test.log(Status.SKIP,"TestCase Skipped :"+result.getName());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
    @Override
    public void onTestStart(ITestResult result) {

    }
}
