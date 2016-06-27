package com.stuart.api.tests;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.provisioning.PlaceProvider;
import com.stuart.api.tests.provisioning.QuotesProvider;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class JobsTest extends ApiTest {

    @Test
    public void createJob() {
        PlaceProvider origin = new PlaceProvider("41.399324", "2.1612412");
        PlaceProvider destination = new PlaceProvider("41.3963479", "2.1603573");

        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.JOB_QUOTE_ID_PARAM,
                        new QuotesProvider(origin, destination).getQuotes().get(3),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs").then().
                log().ifValidationFails().
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json"));
    }

    @Test
    public void createJobFromPackage() {
        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.PACKAGE_TYPE_ID_PARAM, "2",
                        ApiConst.ORIGIN_ADDRESS_STREET_PARAM,
                        "Carrer+de+Pau+Claris+184%2C+Barcelona",
                        ApiConst.DESTINATION_ADDRESS_STREET_PARAM,
                        "Carrer+de+Pau+Claris+186%2C+Barcelona",
                        ApiConst.ORIGIN_PLACE_COMMENT_PARAM, "test+origin+comment",
                        ApiConst.DESTINATION_PLACE_COMMENT_PARAM, "test+destination+comment",
                        ApiConst.CITY_ID_PARAM, "3",
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs/package").then().
                log().ifValidationFails().
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json"));
    }

    //TODO complete @Test
    public void createDuplicatedJob() {
        PlaceProvider origin = new PlaceProvider("41.399324", "2.1612412");
        PlaceProvider destination = new PlaceProvider("41.3963479", "2.1603573");
        String jobQuote = new QuotesProvider(origin, destination).getQuotes().get(3);

        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.JOB_QUOTE_ID_PARAM, jobQuote,
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs");

        given().
                log().ifValidationFails().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.JOB_QUOTE_ID_PARAM, jobQuote,
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientId()).
                expect().statusCode(200).
                when().post("/v1/jobs").then().
                log().ifValidationFails().
                body("errors.code", equalTo(400428),
                        "errors.message", equalTo("job quote already exist. Duplicate job"),
                        "errors.key", equalTo("JOB_QUOTE_ALREADY_EXIST"));
    }
}
