package com.stuart.api.tests.v2;

import com.stuart.api.ApiConst;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertTrue;

public class AddressTest extends ApiV2Test {

    public static final String PICKING = "picking";
    public static final String DELIVERING = "delivering";
    public static final String ADDRESS = "Carrer de Pau Claris, 186 Barcelona";
    public static final String PHONE = "+34666777555";

    @Test
    public void shouldValidatePuAddress() throws Exception {
        Response response = validateAddress(ADDRESS, PHONE, PICKING);

        assertTrue(Boolean.valueOf(response.getBody().jsonPath().getString("success")));
    }

    @Test
    public void shouldValidateDoAddress() throws Exception {
        Response response = validateAddress(ADDRESS, PHONE, DELIVERING);

        assertTrue(Boolean.valueOf(response.getBody().jsonPath().getString("success")));
    }

    @Test
    public void shouldGetPuAddressGeocode() throws Exception {
        checkAddressGeocode(ADDRESS, PICKING);
    }

    @Test
    public void shouldGetDoAddressGeocode() throws Exception {
        checkAddressGeocode(ADDRESS, DELIVERING);
    }

    private void checkAddressGeocode(String puAddress, String addressType) {
        given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(200).
                when().get("v2/addresses/geocode?address=" + puAddress + "&type=" + addressType);
    }

    private Response validateAddress(String puAddress, String phone, String addressType) {
        return given().
                    log().ifValidationFails(LogDetail.ALL).
                    contentType(ApiConst.CONTENT_TYPE).
                    headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                    headers("Content-Type", "application/json").
                    expect().statusCode(200).
                    when().get("v2/addresses/validate?address=" + puAddress + "&type=" + addressType + "&phone=" + phone);
    }
}
