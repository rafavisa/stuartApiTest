package com.stuart.api.tests.v2;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.v2.provisioning.JobV2Provider;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.Test;

import java.math.BigDecimal;

import static com.stuart.api.tests.FileHelper.resourceToString;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class GetJobTest extends ApiV2Test {

    @Test
    public void shouldGetJob() throws Exception {
        String jobId = new JobV2Provider(token).getJobId();
        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(200).
                when().get("/v2/jobs/" + jobId).then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldGetSeveralJob() throws Exception {
        String jobId1 = new JobV2Provider(token).getJobId();
        String jobId2 = new JobV2Provider(token).getJobId();
        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(200).
                when().get("/v2/jobs");

        String jobIds = response.getBody().jsonPath().getString("id");
        assertTrue(jobIds.contains(jobId1) && jobIds.contains(jobId2));
    }

    @Test
    public void shouldGetJobPricing() throws Exception {
        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                body(resourceToString("jobBodyPackage.json")).
                expect().statusCode(200).
                when().post("/v2/jobs/pricing");

        assertTrue(new BigDecimal(response.getBody().jsonPath().getString("amount")).
                compareTo(BigDecimal.valueOf(5.5)) == 0);
        assertEquals("EUR", response.getBody().jsonPath().getString("currency"));
    }

    @Test
    public void shouldGetJobEta() throws Exception {
        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                body(resourceToString("jobBodyPackage.json")).
                expect().statusCode(200).
                when().post("/v2/jobs/eta");

        assertTrue(Integer.parseInt(response.getBody().jsonPath().getString("eta")) > 0);
    }
}
