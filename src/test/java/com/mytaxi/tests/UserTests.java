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

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Test(groups = "User")
public class UserTests extends GenericTest {
    private String userName;
    private List<Users> usersList;

    private Logger logger = Logger.getLogger(UserTests.class);

    public UserTests(){
        this.basePath = "/users";
    }

    @BeforeClass
    public void setupUserClass(){
        userName = properties.getProperty("user.UserName");
        usersList = new ArrayList<>();
    }

    @Test
    void getUserList(){
        logger.info("============ Starting the User Tests ============");
        logger.info("============ Fetching the UsersList from Users Json Response ============ \n");
        usersList = RestUtils.getUserListFromJsonPath(GenericService.getResponseBody(basePath));
        logger.debug("==== Total Number of Users : "+usersList.size()+" ====");
        Assert.assertFalse(usersList.isEmpty());
    }

    @Test
    void checkUserJsonSchema(){
        logger.info("============ Validating the JSON Schema of '/users' ============");
        GenericService.checkJsonSchema(basePath).
                body(matchesJsonSchemaInClasspath("JsonSchemas/userSchema.json"));

    }

    @Test(dependsOnMethods = "getUserList")
    void getUserIDForUserName(){
        logger.info("============ Fetching the UserID related to specific User: "+userName+" ============");
        int userId = RestUtils.getUserId(userName, usersList);
        logger.debug("==== Fetched UserID : "+ userId +" ====");
        Assert.assertTrue(userId != 0 );
    }

    @AfterClass
    void userTestTearDown(){
        logger.info("============ User Tests Are Completed ============= \n\n");
    }
}
