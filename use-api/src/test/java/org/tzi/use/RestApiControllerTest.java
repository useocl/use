package org.tzi.use;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestApiControllerTest {
//
//    @BeforeAll
//    public static void setup() {
//        // Set the base URI for RestAssured
//        RestAssured.baseURI = "http://localhost:8080/api"; // Adjust port if necessary
//    }
//
//    @Test
//    public void testCreateMClass() {
//        // Create a sample ClassDTO JSON payload
//        String requestBody = "{\n" +
//                "    \"name_mclass\": \"Class1\",\n" +
//                "    \"attributes\": [\n" +
//                "        {\n" +
//                "            \"name_attr\": \"Attribute1\",\n" +
//                "            \"type\": \"String\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"operations\": [\n" +
//                "        {\n" +
//                "            \"head\": \"Operation1\",\n" +
//                "            \"body\": \"Operation details\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//
//        // Send POST request and validate response
//        given()
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/mclass")
//                .then()
//                .statusCode(201) // Expect HTTP 201 Created
//                .body("name_mclass", equalTo("Class1"))
//                .body("attributes.size()", equalTo(1))
//                .body("attributes[0].name_attr", equalTo("Attribute1"))
//                .body("operations.size()", equalTo(1))
//                .body("operations[0].head", equalTo("Operation1"));
//    }
//
//    @Test
//    public void testCreateDuplicateMClass() {
//        // Create first ClassDTO with name "SameClass"
//        String requestBody = "{\n" +
//                "    \"name_mclass\": \"SameClass\"\n" +
//                "}";
//
//        // Create the first class
//        given()
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/mclass")
//                .then()
//                .statusCode(201); // Expect HTTP 201 Created
//
//        // Attempt to create another class with the same name
//        given()
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/mclass")
//                .then()
//                .statusCode(400); // Expect HTTP 400 Bad Request or appropriate error code
//    }
//
//    @Test
//    public void testCreateMClassWithoutName() {
//        // Create ClassDTO with no name but with attributes and operations
//        String requestBody = "{\n" +
//                "    \"attributes\": [\n" +
//                "        {\n" +
//                "            \"name_attr\": \"Attribute1\",\n" +
//                "            \"type\": \"String\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"operations\": [\n" +
//                "        {\n" +
//                "            \"head\": \"Operation1\",\n" +
//                "            \"body\": \"Operation details\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//
//        given()
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/mclass")
//                .then()
//                .statusCode(400); // Expect HTTP 400 Bad Request due to missing name_mclass
//    }
//
//    @Test
//    public void testCreateMClassWithEmptyPayload() {
//        // Create ClassDTO with empty payload
//        String requestBody = "{}";
//
//        given()
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/mclass")
//                .then()
//                .statusCode(400); // Expect HTTP 400 Bad Request due to empty payload
//    }
//
//    @Test
//    public void testCreateMClassWithInvalidJson() {
//        // Create ClassDTO with invalid JSON structure
//        String requestBody = "{\n" +
//                "    \"name_mclass\": \"Class1\",\n" +
//                "    \"attributes\": [\n" +
//                "        {\n" +
//                "            \"name_attr\": \"Attribute1\",\n" +
//                "            \"type\": \"String\"\n" +
//                "        },\n" +
//                "    ],\n" + // Extra comma here to create invalid JSON
//                "    \"operations\": [\n" +
//                "        {\n" +
//                "            \"head\": \"Operation1\",\n" +
//                "            \"body\": \"Operation details\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
//
//        given()
//                .contentType("application/json")
//                .body(requestBody)
//                .when()
//                .post("/mclass")
//                .then()
//                .statusCode(400); // Expect HTTP 400 Bad Request due to invalid JSON
//    }

}
