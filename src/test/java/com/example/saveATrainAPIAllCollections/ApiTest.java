package com.example.saveATrainAPIAllCollections;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class ApiTest {
    private static String token;
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://apibook.saveatrain.com";
    }

    @BeforeEach
    public void setupSpecifications() {
        requestSpec = with()
                .contentType("application/json")
                .baseUri(RestAssured.baseURI)
                .log().all();

        responseSpec = RestAssured.expect()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void testPostSalesAgentSessions() {
        Response response = given()
                .spec(requestSpec)
                .body("{\"email\": \"rafal@saveatrain.com\", \"password\": \"Aa123456\"}")
                .when()
                .post("/api/sales_agent_sessions")
                .then()
                .spec(responseSpec)
                .extract()
                .response();

        System.out.println("Response Body: " + response.getBody().asString());

        if (response.jsonPath().get("access_token") != null) {
            token = response.jsonPath().getString("token");
            System.out.println("Token: " + token);
        } else {
            System.out.println("No token found in the response.");
        }
    }
}
