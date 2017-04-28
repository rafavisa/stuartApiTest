package com.stuart.api.tests.v2;

import com.stuart.api.ApiConst;
import com.stuart.api.tests.v2.provisioning.JobV2Provider;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static junit.framework.Assert.assertTrue;

public class InvoicesTest extends ApiV2Test {

    public static final String INVOICE_ID1_QA = "J-D128-4445";
    public static final String INVOICE_ID2_QA = "J-D128-4444";

    @Test
    public void shouldGetClientInvoiceByJobId() throws Exception {
        String jobId = new JobV2Provider(token).getJobId();

        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(202).
                when().get("/v2/invoices/zip?job_ids=" + jobId);

        assertTrue(Boolean.valueOf(response.getBody().jsonPath().getString("success")));
    }

    @Test
    public void shouldGetClientInvoiceByJobIds() throws Exception {
        String jobId1 = new JobV2Provider(token).getJobId();
        String jobId2 = new JobV2Provider(token).getJobId();

        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(202).
                when().get("/v2/invoices/zip?job_ids=" + jobId1 + "," + jobId2);

        assertTrue(Boolean.valueOf(response.getBody().jsonPath().getString("success")));
    }

    @Test
    public void shouldGetClientInvoiceById() throws Exception {
        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(202).
                when().get("/v2/invoices/zip?invoice_ids=" + INVOICE_ID1_QA);

        assertTrue(Boolean.valueOf(response.getBody().jsonPath().getString("success")));
    }

    @Test
    public void shouldGetClientInvoiceByIds() throws Exception {
        Response response = given().
                log().ifValidationFails(LogDetail.ALL).
                contentType(ApiConst.CONTENT_TYPE).
                headers(ApiConst.AUTHORIZATION_HEADER, ApiConst.BEARER + token).
                headers("Content-Type", "application/json").
                expect().statusCode(202).
                when().get("/v2/invoices/zip?invoice_ids=" + INVOICE_ID1_QA + "," + INVOICE_ID2_QA);

        assertTrue(Boolean.valueOf(response.getBody().jsonPath().getString("success")));
    }
}
