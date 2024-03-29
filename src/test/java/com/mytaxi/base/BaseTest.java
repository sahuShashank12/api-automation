package com.mytaxi.base;

import com.mytaxi.utility.ConfigProperty;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeSuite;

import java.util.*;

public class BaseTest {

    public static RequestSpecification httpRequest;
    public static Properties properties;
    public Logger logger;


    public BaseTest() {
        super();
    }

    @BeforeSuite
    public void setUp() {

        logger = Logger.getLogger("myTaxiApiTestLogs");
        PropertyConfigurator.configure("log4j.properties");
        logger.setLevel(Level.INFO);
        properties = ConfigProperty.loadProperties();
        RestAssured.baseURI = properties.getProperty("api.BaseUri");
        httpRequest = RestAssured.given();


    }


}

