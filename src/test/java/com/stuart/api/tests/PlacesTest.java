package com.stuart.api.tests;

import com.stuart.api.ApiConst;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PlacesTest extends ApiTest {

    @Test
    public void createPlace() {
        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.ADDRESS_LATITUDE_PARAM, "41.399324",
                        ApiConst.ADDRESS_LONGITUDE_PARAM, "2.1612412",
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId(),
                        ApiConst.PLACE_TYPE_ID_PARAM, "2").
                expect().statusCode(200).
                when().post("/v1/places").then().
                log().ifValidationFails().
                assertThat().body(matchesJsonSchemaInClasspath("places.json"));
    }
}
