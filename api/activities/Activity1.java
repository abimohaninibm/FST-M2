package activities;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class Activity1 {
	final static String BASE_URI = "https://petstore.swagger.io/v2/pet";
	
	@Test(priority=1)
	public void postAPITestCase() throws FileNotFoundException {
		FileInputStream fis = new FileInputStream("src/test/java/activities/postPayload.json");
		System.out.println("-----------------------------------Post API");
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(fis)
				.when().post(BASE_URI);
		
		System.out.println(response.getBody().asPrettyString());
		System.out.println(">>>> Status Code : "+response.statusCode());
		
		//assertions
		response.then().statusCode(200);
		response.then().body("id", equalTo(77122332));
		response.then().body("name", equalTo("Abi1"));
		response.then().body("status", equalTo("alive"));
		
	}
	
	@Test(priority=2)	
	public void getAPITestCase() {
		
		System.out.println("-----------------------------------Get API");
		Response response = given()
				.contentType(ContentType.JSON)
				.pathParam("petId", "77122332")
				.when()
				.get(BASE_URI+"/{petId}");
		
		
		System.out.println(response.getBody().asPrettyString());
		System.out.println(">>>> Status Code : "+response.statusCode());
		response.then().assertThat().statusCode(200);
		response.then().body("id", equalTo(77122332));
		response.then().body("name", equalTo("Abi1"));
		response.then().body("status", equalTo("alive"));
	}
	
	@Test(priority=3)
	public void deleteAPITestCase() {
		System.out.println("-----------------------------------Delete API");
		
		Response response = given()
				.contentType(ContentType.JSON)
				.pathParam("petId", "77122332")
				.when()
				.delete(BASE_URI+"/{petId}");
		
		System.out.println(response.getBody().asPrettyString());
		System.out.println(">>>> Status code : "+response.statusCode());
		
		//assertions
		response.then().assertThat().statusCode(200);
		response.then().body("code", equalTo(200));
		response.then().body("message",equalTo("77122332"));
		
		
		
		
	}

	@BeforeClass
	public void beforeClass() {
		
	}

	@AfterClass
	public void afterClass() {
	}

}
