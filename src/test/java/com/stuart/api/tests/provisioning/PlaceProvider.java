package com.stuart.api.tests.provisioning;

import com.stuart.api.ApiConst;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PlaceProvider extends Provider {

    private String id;
    private String street;

    public PlaceProvider(String lat, String lon) {
        Response response = given().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + new TokenProvider()
                        .getToken()).
                formParams(
                        ApiConst.ADDRESS_LATITUDE_PARAM, lat,
                        ApiConst.ADDRESS_LONGITUDE_PARAM, lon,
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId(),
                        ApiConst.PLACE_TYPE_ID_PARAM, "2").
                when().post("/v1/places");
        id = response.getBody().jsonPath().getString("id");
        street = response.getBody().jsonPath().getString("address.street");
    }

    public String getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }
}
