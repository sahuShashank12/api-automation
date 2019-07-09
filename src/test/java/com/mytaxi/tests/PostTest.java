package com.mytaxi.tests;

import com.mytaxi.base.GenericTest;
import com.mytaxi.model.Post;
import com.mytaxi.model.Users;
import com.mytaxi.utility.GenericService;
import com.mytaxi.utility.RestUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Test(groups = "Post")
public class PostTest extends GenericTest {
    Logger postLogger = Logger.getLogger(PostTest.class);
   // private GenericService genericRestCall;
    private int userId;
    private List<Post> postList;
    private String userName;
    private List<Users> usersList;
    @BeforeClass
    public void setupUserClass(){
        userName = properties.getProperty("user.UserName");
        usersList = new ArrayList<>();
        postList = new ArrayList<>();
    }
    public PostTest(){
        super();
//        this.basePath = properties.getProperty("post.BasePath");
        this.basePath = "/posts";
    }
    @Test//(groups = {"Post"},dependsOnGroups = {"User","Generic"})
    void getPostListForUserID(){
        postLogger.info("============ Starting the Posts Tests ============");
      //  postLogger.info("============ Fetching the list of Posts From Post Response by Specific UserID: "+userId+" ============ \n");
        String userBasePath = properties.getProperty("user.BasePath");
        usersList = RestUtils.getUserListFromJsonPath(GenericService.getResponseBody(userBasePath));
        userId = RestUtils.getUserId(userName,usersList);
        postList = RestUtils.getPostListFromJsonPath(GenericService.getResponseBodyForQueryParam("userId",userId,basePath));
        postLogger.info("==== Total Number of Posts : "+postList.size()+" ====");
        Assert.assertFalse(postList.isEmpty());
    }

    @Test//(priority = 1,groups = {"Post"},dependsOnGroups = {"User","Generic"})
    void checkPostJsonSchema(){
        postLogger.info("============ Validating the JSON Schema of '/posts' ============");
        GenericService.checkJsonSchema(basePath).
                body(matchesJsonSchemaInClasspath("postSchema.json"));
    }

    @AfterClass
    void postTestTearDown(){
        postLogger.info("============ Posts Tests Are Completed ============= \n\n");
    }
}
