package com.stuart.api.tests.v1.provisioning;

import com.stuart.api.ApiConst;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class QuotesProvider extends Provider {

    Map<Integer, String> quotes = new HashMap<Integer, String>();

    public QuotesProvider(PlaceProvider orig, PlaceProvider dest) {
        Response response = given().
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER,
                        ApiConst.BEARER + new TokenProvider().getToken()).
                formParams(
                        ApiConst.TRANSPORT_TYPE_IDS_PARAM, "1,2,3,4,5",
                        ApiConst.ORIGIN_PLACE_ID_PARAM, orig.getId(),
                        ApiConst.DESTINATION_PLACE_ID_PARAM, dest.getId(),
                        ApiConst.CLIENT_ID_PARAM, testEnv.getClientApiId()).
                when().post("/v1/jobs/quotes/types");
        quotes.put(1, response.getBody().jsonPath().getString("1.id"));
        quotes.put(2, response.getBody().jsonPath().getString("2.id"));
        quotes.put(3, response.getBody().jsonPath().getString("3.id"));
        quotes.put(4, response.getBody().jsonPath().getString("4.id"));
        quotes.put(5, response.getBody().jsonPath().getString("5.id"));
    }

    public Map<Integer, String> getQuotes() {
        return quotes;
    }
}
