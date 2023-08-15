package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.teamwork.constant.Constant.*;
import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatShelterControllerAPITest {

	@BeforeAll
	public static void init() {
		baseURI = "http://localhost";
		port = 8080;
		basePath = "/CatShelter";
	}

	@Test
	@Order(1)
	public void addPetToShelterTest() {
		given().pathParams("name", FULL_NAME).pathParams("age", AGE).pathParam("disability", DISABILITY).pathParam("comments", COMMENTS)
				.when().post("/name={name}/age={age}/disability={disability}/comments={comments}")
				.then().statusCode(200);
	}

	@Test
	@Order(2)
	public void findPetById() {
		List<CatDTO> cats = Arrays.asList(get("/cats").body().as(CatDTO[].class));
		Optional<CatDTO> cat = cats.stream().filter(c -> c.getName().equals(FULL_NAME)).findFirst();
		String id = String.valueOf(cat.orElseThrow().getId());
		given().pathParam("id", id)
				.when().get("/id={id}")
				.then().statusCode(200).extract().as(CatDTO.class);
	}

	@Test
	@Order(3)
	public void findAllPetsTest() {
		given()
				.when().get("/cats")
				.then().statusCode(200).contentType(ContentType.JSON).extract().as(List.class);
	}

	@Test
	@Order(1000)
	public void deletePetFromShelterTest() {
		List<CatDTO> cats = Arrays.asList(get("/cats").body().as(CatDTO[].class));
		cats.stream()
				.filter(c -> c.getName().equals(FULL_NAME))
				.forEach(c -> {
					String id = String.valueOf(c.getId());
					given().pathParam("id", id)
							.when().delete("/{id}");
				});
	}
}
