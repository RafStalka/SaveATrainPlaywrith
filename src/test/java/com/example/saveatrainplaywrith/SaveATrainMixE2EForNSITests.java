package com.example.saveatrainplaywrith;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.ConfigLoader;
import utils.ExcelReader;

import java.io.IOException;
import java.util.stream.Stream;

public class SaveATrainMixE2EForNSITests {
    private static String NSITok;

    @Disabled("Disabled until DB connection will start.")
    @Test
    public void testAuth() throws JSONException {
        String word_para1 = "email";
        String word_para2 = "password";
        String word_para3 = "access_token";

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getApiBook());

        RequestSpecification requestSpec = requestSpecBuilder.build();

        JSONObject requestBody = new JSONObject();
        requestBody.put(word_para1, ConfigLoader.getInstance().getIEmailVercel());
        requestBody.put(word_para2, ConfigLoader.getInstance().getIPassVercel());

        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(requestBody.toString())
                .post("/admin/sessions");

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody(word_para3, Matchers.notNullValue())
                .build();
        response.then().spec(responseSpec);

        JSONObject jsonResponse = new JSONObject(response.asString());

        try {
            NSITok = jsonResponse.getString(word_para3);
            System.out.println(NSITok.strip());
        } catch(JSONException e) {
            System.out.println("Error parsing" + word_para3 + "from response");
            e.printStackTrace();
        }
    }
    @Disabled("Disabled until DB connection will start.")
    @ParameterizedTest
    @MethodSource("excelDataProvider")
    void testUsingExcelData(String originUID, String destinationUID, String departureDatetime) {
        System.out.println(originUID);
        System.out.println(destinationUID);
        System.out.println(departureDatetime);
        // originUID, destinationUID, and departureDatetime corresponds to the columns in Excel sheet
    }

    static Stream<Arguments> excelDataProvider() throws IOException {
        String filePath = "/Users/rafalst/IdeaProjects/SaveATrainPlaywrith/src/test/resources/ConnectionsNSI.xlsx";
        return ExcelReader.readExcelData(filePath)
                .filter(arg -> isValidRow(arg.get()));
    }

    private static boolean isValidRow(Object[] records) {
        if (records.length != 3) return false;
        for (Object record : records) {
            String str = (String) record;
            if (str == null || str.equals("BLANK") || str.equals("UNKNOWN")) return false;
        }
        return true;
    }
}
