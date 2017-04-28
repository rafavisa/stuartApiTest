package com.stuart.api.tests.v2;

import com.stuart.api.ApiConst;
import io.restassured.filter.log.LogDetail;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AreasTest extends ApiV2Test {

    public static final String ZONE = "madrid";

    @Test
    public void shouldGetDoAddressGeocode() throws Exception {
        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(200).
                when().get("v2/areas/" + ZONE);
    }
}
