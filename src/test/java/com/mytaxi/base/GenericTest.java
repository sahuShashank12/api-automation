package com.mytaxi.base;

import io.restassured.http.Method;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "Generic")
public class GenericTest extends BaseTest {

    protected String basePath;
    Logger genericLogger = Logger.getLogger(GenericTest.class);
    Response response;

    public GenericTest() {
        super();
    }

    @BeforeClass()
    public void setBasePath() throws InterruptedException {
        genericLogger.info("------ Setting the Base Path before Performing the Generic checks ------");
        response = BaseTest.httpRequest.request(Method.GET, basePath);
        Thread.sleep(3);
        genericLogger.info("------ Starting the Generic Test for BasePath: '" + basePath + "'------");
    }

    @Test
    public void checkResponseBody() {
        genericLogger.info("------ Verifying the Response Body ------");
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody);
    }

    @Test
    public void checkStatusCode() {
        genericLogger.info("------ Verifying the StatusCode ------");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void checkStatusLine() {
        genericLogger.info("------ Verifying the StatusLine ------");
        String statusLine = response.getStatusLine();
        Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
    }

    @Test
    public void checkResponseTime() {
        genericLogger.info("------ Verifying the ResponseTime ------");
        long responseTime = response.getTime();
        if (responseTime > 4000) {
            genericLogger.info("Response time is greater than 2000");
        }
        Assert.assertTrue(responseTime < 4000);
    }

}
