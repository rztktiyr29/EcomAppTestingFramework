package com.sdet.framework.api.clients;

import com.sdet.framework.api.constants.ApiEndpoints;
import com.sdet.framework.api.pojo.UserLoginPojo;
import com.sdet.framework.api.specs.ApiSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApiClient {

    public String loginAndGetToken(String email, String password) {
        UserLoginPojo credentials = new UserLoginPojo(email, password);

        Response response = given()
                .spec(ApiSpecs.requestSpec())
                .body(credentials)
                .when()
                .post(ApiEndpoints.LOGIN);

        // Extract the token key from your JSON response (e.g., "token" or "accessToken")
        return response.jsonPath().getString("token");
    }
}