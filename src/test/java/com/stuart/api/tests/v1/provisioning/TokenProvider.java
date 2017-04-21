package com.stuart.api.tests.v1.provisioning;

import com.stuart.api.ApiConst;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TokenProvider extends Provider {

    private String token;

    public TokenProvider() {
        Response response = given().
                contentType(ApiConst.CONTENT_TYPE).
                queryParams(
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId(),
                        ApiConst.CLIENT_SECRET_PARAM, testEnv.getClientApiSecret(),
                        ApiConst.REDIRECT_URI_PARAM, ApiConst.REDIRECT_URI,
                        ApiConst.RESPONSE_TYPE_PARAM, ApiConst.RESPONSE_TYPE,
                        ApiConst.SCOPE_PARAM, ApiConst.SCOPE).
                formParams(
                        ApiConst.USERNAME_PARAM, testEnv.getUsername(),
                        ApiConst.PASSWORD_PARAM, testEnv.getPassword(),
                        ApiConst.GRANT_TYPE_PARAM, ApiConst.GRANT_TYPE_PASS).
                when().post("/v1/clients/login");
        token = response.getBody().jsonPath().getString("token");
    }

    public String getToken() {
        return token;
    }
}
