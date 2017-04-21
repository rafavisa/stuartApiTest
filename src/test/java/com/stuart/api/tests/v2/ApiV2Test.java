package com.stuart.api.tests.v2;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.ApiTest;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;

import java.io.IOException;

import static com.stuart.api.tests.FileHelper.resourceToString;
import static io.restassured.RestAssured.given;

public class ApiV2Test extends ApiTest {

    ValidatableResponse requestJobV2(String requestBodyFile) throws IOException {
        return given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                body(resourceToString(requestBodyFile)).
                expect().statusCode(201).
                when().post("/v2/jobs").then().
                log().ifValidationFails(LogDetail.ALL);
    }
}
