package com.stuart.api.tests.v1.provisioning;

import com.stuart.api.ApiConst;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class JobProvider extends Provider {

    private String id;

    public JobProvider(QuotesProvider quotes) {
        Response response = given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER,
                        ApiConst.BEARER + new TokenProvider().getToken()).
                formParams(
                        ApiConst.JOB_QUOTE_ID_PARAM,
                        quotes.getQuotes().get(3),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                expect().statusCode(200).
                when().post("/v1/jobs");
        id = response.getBody().jsonPath().getString("id");
    }

    public String getId() {
        return id;
    }
}
