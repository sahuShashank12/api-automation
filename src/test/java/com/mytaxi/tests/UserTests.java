package com.mytaxi.tests;

import com.mytaxi.base.GenericTest;
import com.mytaxi.model.Users;
import com.mytaxi.utility.GenericService;
import com.mytaxi.utility.RestUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

//@Listeners(com.mytaxi.utility.ExtReports.class)
@Test(groups = "User")
public class UserTests extends GenericTest {
    public int userId;
    public String userName;
    public List<Users> usersList;

    Logger logger = Logger.getLogger(UserTests.class);

    public UserTests(){
        this.basePath = "/users";
    }

    @BeforeClass
    public void setupUserClass(){
        userName = properties.getProperty("user.UserName");
        usersList = new ArrayList<>();
    }

/**TODO : Negatvie test case for 400,500, and user list null*/
    //@BeforeClass(dependsOnGroups = {"parenttests"},groups = "childtests")
    @Test
    void getUserList(){
        logger.info("============ Starting the User Tests ============");
        logger.info("============ Fetching the UsersList from Users Json Response ============ \n");
        usersList = RestUtils.getUserListFromJsonPath(GenericService.getResponseBody(basePath));
        /* usersList = BaseTest.httpRequest.get("/users").then().extract().body().
                jsonPath().getList("", Users.class);*/
        logger.info("==== Total Number of Users : "+usersList.size()+" ====");
        Assert.assertFalse(usersList.isEmpty());
    }

    @Test//(priority = 1,groups = {"User"},dependsOnGroups = {"Generic"})
    void checkUserJsonSchema(){
        logger.info("============ Validating the JSON Schema of '/users' ============");
        GenericService.checkJsonSchema(basePath).
                body(matchesJsonSchemaInClasspath("JsonSchemas/userSchema.json"));

    }

    @Test(dependsOnMethods = "getUserList")
    void getUserIDForUserName(){
        logger.info("============ Fetching the UserID related to specific User: "+userName+" ============");
        /*Input need to take from external file such as ConfigProperties File*/
        userId = RestUtils.getUserId(userName,usersList);
        logger.info("==== Fetched UserID : "+userId+" ====");
        Assert.assertTrue(userId != 0 );
    }

    @AfterClass
    void userTestTearDown(){
        logger.info("============ User Tests Are Completed ============= \n\n");
    }
}
