package com.stuart.api.tests.v1;

import com.stuart.api.ApiConst;

import com.stuart.api.tests.ApiTest;
import org.junit.Test;

import io.restassured.filter.log.LogDetail;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AuthenticationTest extends ApiTest {

    @Test
    public void oauth() {
        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                queryParams(
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId(),
                        ApiConst.CLIENT_SECRET_PARAM, testEnv.getClientApiSecret(),
                        ApiConst.REDIRECT_URI_PARAM, ApiConst.REDIRECT_URI,
                        ApiConst.RESPONSE_TYPE_PARAM, ApiConst.RESPONSE_TYPE,
                        ApiConst.SCOPE_PARAM, ApiConst.SCOPE,
                        ApiConst.GRANT_TYPE_PARAM, ApiConst.GRANT_TYPE_CREDENTIALS).
                expect().statusCode(200).
                when().post("/oauth/token").then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("oauth.json"));
    }
}
