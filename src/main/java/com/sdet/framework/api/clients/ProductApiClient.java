package com.sdet.framework.api.clients;

import com.sdet.framework.api.constants.ApiEndpoints;
import com.sdet.framework.api.pojo.ProductPojo;
import com.sdet.framework.api.specs.ApiSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductApiClient {

    // PUT request to update an existing product (Requires Auth Token)
    public Response updateProduct(String productId, ProductPojo productPayload, String authToken) {
        return given()
                .spec(ApiSpecs.requestSpecWithAuth(authToken)) // Using the auth spec here!
                .pathParam("id", productId)
                .body(productPayload)
                .when()
                .put(ApiEndpoints.UPDATE_PRODUCT);
    }

    // GET request to fetch a product
    public Response getProduct(String productId) {
        return given()
                .spec(ApiSpecs.requestSpec())
                .pathParam("id", productId)
                .when()
                .get(ApiEndpoints.GET_PRODUCT);
    }
}