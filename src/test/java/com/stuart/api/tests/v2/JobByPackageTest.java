package com.stuart.api.tests.v2;

import io.restassured.filter.log.LogDetail;
import org.junit.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JobByPackageTest extends ApiV2Test {

    @Test
    public void shouldCreateJobByPackage() throws Exception {
        requestJobV2("jobBodyPackage.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldCreateStackedJobByPackage() throws Exception {
        requestJobV2("jobBodyStackedPackage.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldCreateScheduledPuJobByPackageSize() throws Exception {
        editJsonFile("jobBodyPackageScheduledPuPackage.json", "/job", "pickup_at", getTomorrowAtNoonDate());
        requestJobV2("jobBodyPackageScheduledPuPackage.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }

    @Test
    public void shouldCreateScheduledDoJobByPackageSize() throws Exception {
        editJsonFile("jobBodyPackageScheduledDoPackage.json", "job", "dropoff_at", getTomorrowAtNoonDate());
        requestJobV2("jobBodyPackageScheduledDoPackage.json").
                assertThat().body(matchesJsonSchemaInClasspath("jobs.json")).
                log().ifValidationFails(LogDetail.ALL);
    }
}
