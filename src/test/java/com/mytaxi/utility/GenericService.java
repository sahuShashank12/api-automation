package com.mytaxi.utility;

import com.mytaxi.base.BaseTest;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.response.ValidatableResponse;

public class GenericService {

    public static ResponseBodyExtractionOptions getResponseBodyForQueryParam(String paramName, int paramValue, String path) {
        ResponseBodyExtractionOptions body = BaseTest.httpRequest.
                param(paramName, paramValue).
                get(path).
                then().
                extract().
                body();

        return body;
    }

    public static ResponseBodyExtractionOptions getResponseBody(String path) {
        ResponseBodyExtractionOptions body = BaseTest.httpRequest.
                get(path).
                then().
                extract().
                body();
        return body;
    }

    public static ValidatableResponse checkJsonSchema(String path) {

        ValidatableResponse result = BaseTest.httpRequest
                .when()
                .get(path)
                .then()
                .assertThat();
        return result;
    }


}
