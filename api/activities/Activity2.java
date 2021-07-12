package activities;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Activity2 {

	final static String Base_uri="https://petstore.swagger.io/v2/user";
	String user="";
	
	@Test(priority=1)
	public void postAPITestCase() throws FileNotFoundException {

		System.out.println("---------------------------------------POST API");
		FileInputStream fis = new FileInputStream("src/test/java/activities/activity2Payload.json");
		Response response = given().contentType(ContentType.JSON).log().all(true)
				.when().body(fis)
				.post(Base_uri);
		
		response.then().assertThat()
		.statusCode(200).log().all();
		
		
					
	}

	@Test(priority=2)
	public void getAPITestCase() throws IOException {

		System.out.println("---------------------------------------GET API");
		
		Response response = given().contentType(ContentType.JSON)
				.log().all()
				.when().pathParam("username", "abimohan")
				.get(Base_uri+"/{username}");
		
		response.then().log().all();
		response.then().statusCode(200);
		response.then().body("id", equalTo(9901231));
		response.then().body("username", equalTo("abimohan"));
		response.then().body("firstName", equalTo("Abishek"));
		response.then().body("lastName", equalTo("Mohan"));
		response.then().body("email", equalTo("justincase@mail.com"));
		response.then().body("password", equalTo("password123"));
		response.then().body("phone", equalTo("9812763450"));
	
		//writing response to a file
		File f = new File("src/test/java/activities/activity2Resp.json");
		f.createNewFile();
		FileWriter fw = new FileWriter(f.getPath());
		fw.write(response.getBody().asPrettyString());
		System.out.println("------------------Write complete");
		fw.close();
		
	}

	@Test(priority=3)
	public void deleteAPITestCase() {

		System.out.println("---------------------------------------DELETE API");
		Response response = given().contentType(ContentType.JSON)
				.log().all()
				.when().pathParam("username", "abimohan")
				.delete(Base_uri+"/{username}");
		
		response.then().log().all().statusCode(200);
		response.then().body("message", equalTo("abimohan"));
		response.then().body("code", equalTo(200));
		
		
		
	}
}
