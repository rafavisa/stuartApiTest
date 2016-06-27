package com.stuart.api.tests;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.provisioning.PlaceProvider;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class QuotesTest extends ApiTest {

    @Test
    public void createQuotesFromPlaceId() {
        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1,2,3,4,5",
                        ApiConst.ORIGIN_PLACE_ID_PARAM,
                        new PlaceProvider("41.399324", "2.1612412").getId(),
                        ApiConst.DESTINATION_PLACE_ID_PARAM,
                        new PlaceProvider("41.3963479", "2.1603573").getId(),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs/quotes/types").then().
                log().ifValidationFails().
                assertThat().body(matchesJsonSchemaInClasspath("quotes.json"));
    }

    @Test
    public void createQuotesFromPlaceStreet() {
        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1,2,3,4,5",
                        ApiConst.ORIGIN_PARAM,
                        new PlaceProvider("41.399324", "2.1612412").getStreet(),
                        ApiConst.DESTINATION_PARAM,
                        new PlaceProvider("41.3963479", "2.1603573").getStreet(),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs/quotes/types").then().
                log().ifValidationFails().
                assertThat().body(matchesJsonSchemaInClasspath("quotes.json"));
    }

    @Test
    public void createQuotesSamePlaceId() {
        String placeId = new PlaceProvider("41.399324", "2.1612412").getId();

        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1",
                        ApiConst.ORIGIN_PLACE_ID_PARAM, placeId,
                        ApiConst.DESTINATION_PLACE_ID_PARAM, placeId,
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs/quotes/types").then().
                log().ifValidationFails().
                body("1.errors.code", equalTo(9999),
                        "1.errors.message", equalTo("Polyline can't be blank"),
                        "1.errors.key", equalTo("JOB_DISTANCE_NOT_ALLOWED"));
    }
}
