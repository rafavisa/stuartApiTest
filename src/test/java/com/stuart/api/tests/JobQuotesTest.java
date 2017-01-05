package com.stuart.api.tests;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.provisioning.PlaceProvider;

import org.junit.Test;

import io.restassured.filter.log.LogDetail;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.contains;

public class JobQuotesTest extends ApiTest {

    @Test
    public void createQuotesFromPlaceId() {
        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1,2,3,4,5",
                        ApiConst.ORIGIN_PLACE_ID_PARAM,
                        new PlaceProvider("41.399324", "2.1612412").getId(),
                        ApiConst.DESTINATION_PLACE_ID_PARAM,
                        new PlaceProvider("41.3963479", "2.1603573").getId(),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                expect().statusCode(200).
                when().post("/v1/jobs/quotes/types").then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("jobQuotes.json"));
    }

    @Test
    public void createQuotesFromPlaceStreet() {
        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1,2,3,4,5",
                        ApiConst.ORIGIN_PARAM,
                        new PlaceProvider("41.399324", "2.1612412").getStreet(),
                        ApiConst.DESTINATION_PARAM,
                        new PlaceProvider("41.3963479", "2.1603573").getStreet(),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                expect().statusCode(200).
                when().post("/v1/jobs/quotes/types").then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("jobQuotes.json"));
    }

    @Test
    public void createQuotesSamePlaceId() {
        String placeId = new PlaceProvider("41.399324", "2.1612412").getId();

        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1",
                        ApiConst.ORIGIN_PLACE_ID_PARAM, placeId,
                        ApiConst.DESTINATION_PLACE_ID_PARAM, placeId,
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                expect().statusCode(422).
                when().post("/v1/jobs/quotes/types").then().
                log().ifValidationFails(LogDetail.ALL).
                body("errors.code", contains(422022),
                        "errors.message", contains("Same origin and destination addresses"),
                        "errors.key", contains("SAME_ORIGIN_AND_DESTINATION"));
    }
}
