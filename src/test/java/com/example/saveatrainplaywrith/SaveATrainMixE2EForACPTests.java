package com.example.saveatrainplaywrith;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.ConfigLoader;
import utils.ExcelReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaveATrainMixE2EForACPTests {
    private final List<String> allIdentifiers = new ArrayList<>();
    private final Map<String, List<String>> identifierToIdsMap = new HashMap<>();
    private final List<String> allIds = new ArrayList<>();
    private static String TITok;

    @Order(1)
    @Test
    public void testAuth() throws JSONException {
        String word_para1 = "email";
        String word_para2 = "password";
        String word_para3 = "access_token";
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getVendor());

        RequestSpecification requestSpec = requestSpecBuilder.build();

        JSONObject requestBody = new JSONObject();
        requestBody.put(word_para1, ConfigLoader.getInstance().getIEmailVercel());
        requestBody.put(word_para2, ConfigLoader.getInstance().getIPassVercel());

        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(requestBody.toString())
                .post(ConfigLoader.getInstance().getVendorEndpoint());

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody(word_para3, Matchers.notNullValue())
                .build();
        response.then().spec(responseSpec);

        JSONObject jsonResponse = new JSONObject(response.asString());

        try {
            TITok = jsonResponse.getString(word_para3);
        } catch (JSONException e) {
            System.out.println("Error parsing" + word_para3 + "from response");
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @MethodSource("excelDataProvider")
    void testUsingExcelDataForSearchConnectionsACP(String originUID, String destinationUID, String departureDatetime) {
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject requestParams = new JSONObject();
        JSONObject searchParams = new JSONObject();

        searchParams.put("departure_datetime", departureDatetime);

        JSONObject passengerAttributes = new JSONObject();
        passengerAttributes.put("age", 21);
        passengerAttributes.put("passenger_type_attributes", new JSONObject().put("type", "Search::PassengerType::Adult"));
        JSONObject passengerMap = new JSONObject();
        passengerMap.put("0", passengerAttributes);
        searchParams.put("searches_passengers_attributes", passengerMap);

        JSONObject routeAttributes = new JSONObject();
        routeAttributes.put("origin_station_attributes", new JSONObject().put("uid", originUID));
        routeAttributes.put("destination_station_attributes", new JSONObject().put("uid", destinationUID));
        searchParams.put("route_attributes", routeAttributes);

        requestParams.put("search", searchParams);

        String agentEmail = ConfigLoader.getInstance().getIEmailVercel();
        String agentToken = TITok;

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getInstance().getVendor() + ConfigLoader.getInstance().getVendorSearchEndpoint())
                .setContentType(ConfigLoader.getInstance().getVendorContentType())
                .addHeader(ConfigLoader.getInstance().getHeaderE(), agentEmail)
                .addHeader(ConfigLoader.getInstance().getHeaderT(), agentToken)
                .setBody(requestParams.toString())
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        Response response = given()
                .spec(requestSpec)
                .when()
                .post()
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        JSONObject originStation = jsonResponse.getJSONObject("route").getJSONObject("origin_station");
        JSONArray originNames = originStation.getJSONArray("names");
        String originStationName = "";
        for (int i = 0; i < originNames.length(); i++) {
            JSONObject originName = originNames.getJSONObject(i);
            String languageName = originName.getJSONObject("language").getString("name");
            if (languageName.equals("English")) {
                originStationName = originName.getString("name");
                break;
            }
        }

        JSONObject destinationStation = jsonResponse.getJSONObject("route").getJSONObject("destination_station");
        JSONArray destinationNames = destinationStation.getJSONArray("names");
        String destinationStationName = "";
        for (int i = 0; i < destinationNames.length(); i++) {
            JSONObject destinationName = destinationNames.getJSONObject(i);
            String languageName = destinationName.getJSONObject("language").getString("name");
            if (languageName.equals("English")) {
                destinationStationName = destinationName.getString("name");
                break;
            }
        }

        if (jsonResponse.has("identifier")) {
            String identifier = jsonResponse.getString("identifier");
            allIdentifiers.add(identifier);
            System.out.println("Identifier: " + identifier);
        }

        JSONArray results = jsonResponse.getJSONArray("results");
        int totalIds = 0;
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            if (result.has("id")) {
                totalIds++;
                String idValue = String.valueOf(result.getInt("id"));
                allIds.add(idValue);
                System.out.println("ID: " + result.getInt("id"));

                if (jsonResponse.has("identifier")) {
                    String identifier = jsonResponse.getString("identifier");
                    identifierToIdsMap.putIfAbsent(identifier, new ArrayList<>());
                    identifierToIdsMap.get(identifier).add(idValue);
                }

                String departureDatetimeResult = "";
                if (result.has("departure_datetime")) {
                    departureDatetimeResult = result.getString("departure_datetime");
                    System.out.println("Departure DateTime: " + departureDatetimeResult);
                } else {
                    System.out.println("Departure DateTime not present in response. Using date from Excel: " + departureDatetime);
                }
            }
        }

        System.out.println(originStationName + " --> " + destinationStationName + " "
                + "Departure request: " + departureDatetime + " " + "Total ids: " + totalIds);
    }

    static Stream<Arguments> excelDataProvider() throws IOException {
        String filePath = System.getProperty("user.dir") + ConfigLoader.getInstance().getFilePathACP();
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
