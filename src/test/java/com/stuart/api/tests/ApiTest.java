package com.stuart.api.tests;

import com.oracle.javafx.jmx.json.JSONException;
import com.stuart.api.Env;
import com.stuart.api.tests.v1.provisioning.TokenProvider;

import org.json.JSONObject;
import org.junit.Before;

import io.restassured.RestAssured;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.stuart.api.tests.FileHelper.resourceToString;

public class ApiTest {

    public String token;
    public Env testEnv = new Env();

    @Before
    public void setUp() {
        RestAssured.baseURI = testEnv.getApiUri();
        this.token = new TokenProvider().getToken();
    }

    public void editJsonFile(String jsonFileName, String jsonPointer, String key, String value)
            throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(resourceToString(jsonFileName));
        JSONObject targetJsonObject = (JSONObject) jsonObject.query(jsonPointer);
        String currentValue = (String) targetJsonObject.get(key);
        if(!value.equals(currentValue)) {
            targetJsonObject.put(key,value);
            try (FileWriter file = new FileWriter("src/test/resources/" + jsonFileName)) {
                file.write(jsonObject.toString());
            }
        }
    }

    public String getTomorrowAtNoonDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Date currentDate = new Date();
        String currentDateString = dateFormat.format(currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(currentDateString));

        calendar.add(Calendar.DATE, 1);

        calendar.set(Calendar.HOUR, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        return dateFormat.format(calendar.getTime());
    }
}
