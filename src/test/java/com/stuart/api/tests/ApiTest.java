package com.stuart.api.tests;

import com.stuart.api.Env;
import com.stuart.api.tests.provisioning.TokenProvider;

import org.junit.Before;

import io.restassured.RestAssured;

public class ApiTest {

    public String token;
    public Env testEnv = new Env();

    @Before
    public void setUp() {
        RestAssured.baseURI = testEnv.getApiUri();
        this.token = new TokenProvider().getToken();
    }
}
