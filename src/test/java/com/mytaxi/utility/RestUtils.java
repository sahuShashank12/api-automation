package com.mytaxi.utility;

import com.mytaxi.model.Comment;
import com.mytaxi.model.Post;
import com.mytaxi.model.Users;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RestUtils {

    public static int getUserId(String name, List<Users> usersList) {
        int userId = 0;
        if (usersList.size() > 0) {
            Optional<Users> users = usersList.stream().filter(user -> name.equals(user.getUsername())).findFirst();
            Users users1 = users.get();
            if (users1 != null) {
                userId = users1.getId();

            }
        }
        return userId;
    }

    public static List<Comment> getCommentListFromJsonPath(ResponseBodyExtractionOptions bodyExtractionOptions) {
        return getJsonPath(bodyExtractionOptions).getList("", Comment.class);
    }

    public static List<Users> getUserListFromJsonPath(ResponseBodyExtractionOptions bodyExtractionOptions) {
        return getJsonPath(bodyExtractionOptions).getList("", Users.class);
    }

    public static List<Post> getPostListFromJsonPath(ResponseBodyExtractionOptions bodyExtractionOptions) {
        return getJsonPath(bodyExtractionOptions).getList("", Post.class);
    }

    public static JsonPath getJsonPath(ResponseBodyExtractionOptions bodyExtractionOptions) {
        return bodyExtractionOptions.jsonPath();
    }

    public static List<Comment> getInvalidEmailCommentList(String emailRegex, Map<Integer, List> postCommentMap) {
        List<Comment> errorCommentList = new ArrayList<>();
        for (Map.Entry<Integer, List> entry : postCommentMap.entrySet()) {
            List<Comment> comments = entry.getValue();
            for (Comment comment : comments) {
                String email = comment.getEmail();
                if (isInvalidEmail(emailRegex, email)) {
                    errorCommentList.add(comment);
                }
            }
        }
        return errorCommentList;
    }

    private static boolean isInvalidEmail(String emailRegex, String email) {
        return StringUtils.isNotEmpty(email) && !email.matches(emailRegex);
    }

}
