package com.stuart.api.tests;

import com.stuart.api.ApiConst;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class LoginTest extends ApiTest {

    @Test
    public void login() {
        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                queryParams(
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId(),
                        ApiConst.CLIENT_SECRET_PARAM, testEnv.getClientSecret(),
                        ApiConst.REDIRECT_URI_PARAM, ApiConst.REDIRECT_URI,
                        ApiConst.RESPONSE_TYPE_PARAM, ApiConst.RESPONSE_TYPE,
                        ApiConst.SCOPE_PARAM, ApiConst.SCOPE).
                formParams(
                        ApiConst.USERNAME_PARAM, testEnv.getUsername(),
                        ApiConst.PASSWORD_PARAM, testEnv.getPassword(),
                        ApiConst.GRANT_TYPE_PARAM, ApiConst.GRANT_TYPE).
                expect().statusCode(200).
                when().post("/v1/clients/login").then().
                log().ifValidationFails().
                assertThat().body(matchesJsonSchemaInClasspath("login.json"));
    }

    @Test
    public void loginWrongEmail() {
        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                queryParams(
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId(),
                        ApiConst.CLIENT_SECRET_PARAM, testEnv.getClientSecret(),
                        ApiConst.REDIRECT_URI_PARAM, ApiConst.REDIRECT_URI,
                        ApiConst.RESPONSE_TYPE_PARAM, ApiConst.RESPONSE_TYPE,
                        ApiConst.SCOPE_PARAM, ApiConst.SCOPE).
                formParams(
                        ApiConst.USERNAME_PARAM, "wrong@email.com",
                        ApiConst.PASSWORD_PARAM, testEnv.getPassword(),
                        ApiConst.GRANT_TYPE_PARAM, ApiConst.GRANT_TYPE).
                expect().statusCode(404).
                when().post("/v1/clients/login").then().
                log().ifValidationFails().
                body("errors[0].code", equalTo(404025),
                        "errors[0].message", equalTo("The email is not correct"),
                        "errors[0].key", equalTo("LOGIN_EMAIL_INCORRECT"));
    }
}
