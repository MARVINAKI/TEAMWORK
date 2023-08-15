package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatVolunteerDTO;
import com.example.teamwork.constant.Constant;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatVolunteerControllerAPITest {

	@BeforeAll
	public static void init() {
		baseURI = "http://localhost";
		port = 8080;
		basePath = "/catVolunteer";
	}

	@Test
	@Order(1)
	public void addVolunteerTest() {
		given().pathParam("fullName", Constant.FULL_NAME)
				.when().post("/full_name={fullName}")
				.then().statusCode(200);
	}

	@Test
	@Order(2)
	public void findAllVolunteers() {
		given()
				.when().get("/volunteers")
				.then().statusCode(200).contentType(ContentType.JSON).extract().as(List.class);
	}

	@Test
	@Order(3)
	public void findVolunteerById() {
		List<CatVolunteerDTO> volunteers = Arrays.asList(get("/volunteers").getBody().as(CatVolunteerDTO[].class));
		String id = String.valueOf(volunteers.stream().filter(v -> v.getFullName().equals(Constant.FULL_NAME)).findFirst().orElseThrow().getId());
		given().pathParam("id", id)
				.when().get("/{id}")
				.then().statusCode(200).contentType(ContentType.JSON).extract().as(CatVolunteerDTO.class);
	}

	@Test
	@Order(1000)
	public void deleteVolunteerById() {
		List<CatVolunteerDTO> volunteers = Arrays.asList(get("/volunteers").getBody().as(CatVolunteerDTO[].class));
		volunteers.stream()
				.filter(v -> v.getFullName().equals(Constant.FULL_NAME))
				.forEach(v->{
					String id = String.valueOf(v.getId());
					given().pathParam("id", id)
							.when().delete("/{id}");
				});
	}
}
