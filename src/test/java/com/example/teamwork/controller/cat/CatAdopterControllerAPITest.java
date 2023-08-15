package com.example.teamwork.controller.cat;

import com.example.teamwork.DTO.cat.CatAdopterDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatAdopterControllerAPITest {

	private static final String TEST_NAME = "TestName";

	@BeforeAll
	public static void init() {
		baseURI = "http://localhost";
		port = 8080;
		basePath = "/cat_adopters";
		given().pathParam("name", TEST_NAME)
				.when().get("/name={name}")
				.then().statusCode(404);
	}

	@Test
	@Order(1)
	public void addAdopterTest() {
		given().pathParams("chatID", "1122334455")
				.pathParams("fullName", TEST_NAME)
				.pathParams("phoneNumber", "11111111111")
				.when()
				.post("/chat_id={chatID}/full_name={fullName}/phone_number={phoneNumber}")
				.then().statusCode(200);
	}

	@Test
	@Order(1)
	public void errorAddAdopterTest() {
		given().pathParams("chatID", "wrongId")
				.pathParams("fullName", TEST_NAME)
				.pathParams("phoneNumber", "1")
				.when()
				.post("/chat_id={chatID}/full_name={fullName}/phone_number={phoneNumber}")
				.then().statusCode(400);
	}

	@Test
	@Order(2)
	public void findAdopterByNameTest() {
		given().pathParams("name", TEST_NAME)
				.when().get("/name={name}")
				.then().statusCode(200).extract().as(CatAdopterDTO.class);
	}

	@Test
	@Order(2)
	public void noFoundAdopterByName() {
		given().pathParams("name", TEST_NAME + TEST_NAME)
				.when().get("/name={name}")
				.then().statusCode(404);
	}

	@Test
	@Order(3)
	public void findAllAdoptersTest() {
		given()
				.when().get("/adopters")
				.then().statusCode(200).contentType(ContentType.JSON)
				.extract().as(List.class);
	}

	@Test
	@Order(4)
	public void findAdopterByIdTest() {
		String id = given().pathParams("name", TEST_NAME)
				.when().get("/name={name}")
				.then().statusCode(200)
				.extract().path("id").toString();
		given().pathParams("id", id)
				.when().get("/id={id}")
				.then().statusCode(200);
	}

	@Test
	@Order(4)
	public void noFoundAdopterById() {
		given().pathParams("id", 0)
				.when().get("/id={id}")
				.then().statusCode(404);
	}

	@Test
	@Order(1000)
	public void deleteAdopterTest() {
		List<CatAdopterDTO> adopters = Arrays.asList(get("/adopters").getBody().as(CatAdopterDTO[].class));
		adopters.stream()
				.filter(a -> a.getFullName().equals(TEST_NAME))
				.forEach(a -> {
					String id = String.valueOf(a.getId());
					given().pathParam("id", id)
							.when().delete("/id={id}");
				});
	}
}
