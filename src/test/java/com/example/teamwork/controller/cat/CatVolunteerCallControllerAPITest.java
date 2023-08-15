package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatVolunteerCallDTO;
import com.example.teamwork.model.CatVolunteerCall;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatVolunteerCallControllerAPITest {

	@BeforeAll
	public static void init() {
		baseURI = "http://localhost";
		port = 8080;
		basePath = "/catVolunteerCall";
	}

	@Test
	@Order(1)
	public void findVolunteerCallByIdTest() {
		List<CatVolunteerCallDTO> calls = Arrays.asList(get("/calls").getBody().as(CatVolunteerCallDTO[].class));
		if (!calls.isEmpty()) {
			String id = String.valueOf(calls.get(0).getId());
			given().pathParam("id", id)
					.when().get("/{id}")
					.then().statusCode(200).extract().as(CatVolunteerCall.class);
		}
	}

	@Test
	@Order(2)
	public void findAllCalls() {
		given()
				.when().get("/calls")
				.then().statusCode(200).contentType(ContentType.JSON).extract().as(List.class);
	}

	@Test
	@Order(1000)
	public void closeCalls() {
		given().pathParam("id", -1)
				.when().delete("/{id}")
				.then().statusCode(500);
	}
}
