package com.stuart.api.tests.v2.provisioning;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.v1.provisioning.Provider;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;

import java.io.IOException;

import static com.stuart.api.tests.FileHelper.resourceToString;
import static io.restassured.RestAssured.given;

public class JobV2Provider extends Provider {

    private String id;
    private String[] deliveries;

    public JobV2Provider(String token) throws IOException {
        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                body(resourceToString("jobBodyPackage.json")).
                expect().statusCode(201).
                when().post("/v2/jobs");

        id = response.getBody().jsonPath().getString("id");
        deliveries= response.getBody().jsonPath().getString("deliveries.id").replace("[","").replace("]","").split(",");
    }

    public String getJobId() {
        return id;
    }
    public String[] getDeliveryIds() {
        return deliveries;
    }
}
