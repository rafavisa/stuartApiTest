package com.stuart.api.tests.v1;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.ApiTest;
import com.stuart.api.tests.v1.provisioning.JobProvider;
import com.stuart.api.tests.v1.provisioning.PlaceProvider;
import com.stuart.api.tests.v1.provisioning.QuotesProvider;

import org.junit.Test;

import io.restassured.filter.log.LogDetail;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JobsTest extends ApiTest {

    @Test
    public void createJob() {
        QuotesProvider quotesProvider = getQuotesProvider();

        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                formParams(
                        ApiConst.JOB_QUOTE_ID_PARAM,
                        quotesProvider.getQuotes().get(3),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                expect().statusCode(200).
                when().post("/v1/jobs").then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json"));
    }

    @Test
    public void createJobByPackageSize() {
        given().
                log().ifValidationFails(LogDetail.ALL).
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
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                expect().statusCode(200).
                when().post("/v1/jobs/package").then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json"));
    }

    @Test
    public void queryJob() {
        QuotesProvider quotesProvider = getQuotesProvider();
        String jobId = new JobProvider(quotesProvider).getId();
        String url = "/v1/jobs/" + jobId;

        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                expect().statusCode(200).
                when().get(url).then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("queryJob.json"));
    }

    @Test
    public void cancelJob() {
        QuotesProvider quotesProvider = getQuotesProvider();
        String jobId = new JobProvider(quotesProvider).getId();
        String url = "/v1/clients/" + testEnv.getClientId() + "/jobs/" + jobId + "/cancel";

        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                expect().statusCode(200).
                when().post(url).then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("cancelJob.json"));
    }

    private QuotesProvider getQuotesProvider() {
        PlaceProvider origin = new PlaceProvider("41.399324", "2.1612412");
        PlaceProvider destination = new PlaceProvider("41.3963479", "2.1603573");
        return new QuotesProvider(origin, destination);
    }
}
