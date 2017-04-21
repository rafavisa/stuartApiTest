package com.stuart.api.tests.v2;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.v2.provisioning.JobV2Provider;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.Test;

import static com.stuart.api.tests.FileHelper.resourceToString;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class EditJobTest extends ApiV2Test {

    @Test
    public void shouldGetJob() throws Exception {
        JobV2Provider jobV2Provider = new JobV2Provider(token);
        String jobId = jobV2Provider.getJobId();
        String[] deliveryIds = jobV2Provider.getDeliveryIds();
        editJsonFile("jobBodyPatch.json","/job/deliveries/0", "id", deliveryIds[0]);
        String uniqueClientReference = String.valueOf(System.currentTimeMillis());
        editJsonFile("jobBodyPatch.json","/job/deliveries/0", "client_reference", uniqueClientReference);

        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                body(resourceToString("jobBodyPatch.json")).
                expect().statusCode(200).
                when().patch("/v2/jobs/" + jobId);

        response.then().
                log().ifValidationFails(LogDetail.ALL).
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
        assertEquals(uniqueClientReference, response.getBody().jsonPath().get("deliveries[0].client_reference"));
        assertEquals("patch", response.getBody().jsonPath().get("deliveries[0].package_description"));
        assertEquals("patch", response.getBody().jsonPath().get("deliveries[0].pickup.comment"));
    }
}
