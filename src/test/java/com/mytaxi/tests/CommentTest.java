package com.mytaxi.tests;

import com.mytaxi.base.GenericTest;
import com.mytaxi.model.Comment;
import com.mytaxi.model.Post;
import com.mytaxi.model.Users;
import com.mytaxi.utility.GenericService;
import com.mytaxi.utility.RestUtils;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Test(groups = "Comment")
public class CommentTest extends GenericTest {
    public int userId;
    public String userName;
    public List<Users> usersList;
    public List<Post> postList;
    public List<Comment> commentList;
    public Map<Integer, List> postCommentMap = new HashMap<>();

    Logger commentLogger = Logger.getLogger(CommentTest.class);

    public CommentTest() {
        //this.basePath = this.basePath = properties.getProperty("comment.BasePath");
        this.basePath = "/comments";
    }

    @BeforeClass
    public void setupUserClass(){
        userName = properties.getProperty("user.UserName");
        usersList = new ArrayList<>();
        postList = new ArrayList<>();
    }

    @Test//(groups = {"Comment"},dependsOnGroups = {"Post","Generic"})
    void getCommentListForPostID() {
        commentLogger.info("============ Starting the Comments Tests ============");
        commentLogger.info("============ Fetching the CommentsList from Comment Json Response ============ \n");
        String userBasePath = properties.getProperty("user.BasePath");
        String postBasePath = properties.getProperty("post.BasePath");
        usersList = RestUtils.getUserListFromJsonPath(GenericService.getResponseBody(userBasePath));
        userId = RestUtils.getUserId(userName,usersList);
        postList = RestUtils.getPostListFromJsonPath(GenericService.getResponseBodyForQueryParam("userId",userId,postBasePath));

        for (Post p : postList) {
            ResponseBodyExtractionOptions body = GenericService.getResponseBodyForQueryParam("postId", p.getId(), basePath);
            commentList = RestUtils.getCommentListFromJsonPath(body);
            postCommentMap.put(p.getId(), commentList);
        }
        commentLogger.info("==== Total Number of Comments : "+commentList.size()+" ====");
        Assert.assertFalse(commentList.isEmpty());
    }

    @Test//(priority = 1,groups = {"Comment"},dependsOnGroups = {"Post","Generic"})
    void checkUserJsonSchema(){
        commentLogger.info("============ Validating the JSON Schema of '/comments' ============");
        GenericService.checkJsonSchema(basePath).
                body(matchesJsonSchemaInClasspath("commentSchema.json"));
    }


    @Test//(priority = 2,groups = {"Comment"},dependsOnGroups = {"Post","Generic"})
    void verifyEmailId() {
                                                                                                // String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        commentLogger.info("============ Performing the Validations on the Email ID ============");
        String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if( !postCommentMap.isEmpty() ) {
            List<Comment> errorCommentList = RestUtils.getInvalidEmailCommentList(emailRegex, postCommentMap);
            System.out.println();
            commentLogger.info("==== Total Number of Comments with Invalid Email ID : "+errorCommentList.size()+" ====");
            commentLogger.error(Collections.singletonList(errorCommentList).toString());
            Assert.assertTrue("Invalid Email's List Is Not Empty ", errorCommentList.isEmpty());
        }
        else {
            commentLogger.info("============ Validations on the Email ID have Failed============");
            Assert.fail("The Post ID to CommentList Mapping is Empty");
        }
    }

    @AfterClass
    void commentTestTearDown(){
        commentLogger.info("============ Comments Tests Are Completed ============= \n\n");
    }

}
