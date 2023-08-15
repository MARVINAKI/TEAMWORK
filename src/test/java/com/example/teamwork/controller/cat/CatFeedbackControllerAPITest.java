package com.example.teamwork.controller.cat;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatFeedbackControllerAPITest {

	@BeforeAll
	public static void init() {
		baseURI = "http://localhost";
		port = 8080;
		basePath = "/catFeedback";
	}

	@Test
	@Order(1)
	public void findAllOpenFeedbacksTest() {
		given()
				.when().get("/requests")
				.then().statusCode(200).contentType(ContentType.JSON)
				.extract().as(List.class);
	}

	@Test
	@Order(1000)
	public void deleteFeedbackTest() {
		given().pathParams("id", 0)
				.when().delete("/id={id}")
				.then().statusCode(400);
	}
}
