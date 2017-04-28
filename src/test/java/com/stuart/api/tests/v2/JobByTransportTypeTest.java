package com.stuart.api.tests.v2;

import io.restassured.filter.log.LogDetail;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JobByTransportTypeTest extends ApiV2Test {

    @Test
    public void shouldCreateJobByTransportType() throws Exception {
        requestJobV2("jobBodyTransportType.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldCreateStackedJobByTransportType() throws Exception {
        requestJobV2("jobBodyStackedTransportType.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldCreateScheduledPuJobByTransportType() throws Exception {
        editJsonFile("jobBodyPackageScheduledPuTransportType.json", "/job", "pickup_at", getTomorrowAtNoonDate());
        requestJobV2("jobBodyPackageScheduledPuTransportType.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldCreateScheduledDoJobByTransportType() throws Exception {
        editJsonFile("jobBodyPackageScheduledDoTransportType.json", "/job", "dropoff_at", getTomorrowAtNoonDate());
        requestJobV2("jobBodyPackageScheduledDoTransportType.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }
}
