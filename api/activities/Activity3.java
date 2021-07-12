package activities;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


public class Activity3 {
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	
		@BeforeClass
	public void beforeClass() {
			reqSpec = new RequestSpecBuilder()
					.setBaseUri("https://petstore.swagger.io/v2/pet")
					.setContentType(ContentType.JSON)
					.build();
			
			resSpec = new ResponseSpecBuilder()
					.expectContentType(ContentType.JSON)
					.expectStatusCode(200)
					.build();
	}
		
	@DataProvider
	public Object[][] petInfo(){
		Object[][] petInfo = new Object[][] {
			{ 77232, "Riley", "alive" }, 
		    { 77233, "Hansel", "alive" }
		};
		return petInfo;
	}

	@Test( dataProvider="petInfo" , priority=1)
	public void postAPITest(int id, String name, String status) throws InterruptedException {
		
		System.out.println("----------------------------->> POST API - id : "+id);
		Response response = given()
				.spec(reqSpec)
				.when().body("{\"id\": "+id+", \"name\": \""+name+"\", \"status\": \""+status+"\"}")
				.log().uri()
				.post();
		
		response.then().spec(resSpec);
		response.then().log().body();
		response.then().body("status", equalTo(status));
		Thread.sleep(2000);
		
	}
	
	@Test( dataProvider="petInfo" , priority=2)
	public void getAPITest(int id, String name, String status) throws InterruptedException {
		
		System.out.println("----------------------------->> GET API - id : "+id);
		Response response = given()
				.spec(reqSpec)
				.when().pathParam("petId", id)
				.log().uri()
				.get("/{petId}");
		
		response.then().spec(resSpec);
		response.then().log().body();
		//assertion
		Thread.sleep(2000);
	}
	
	@Test(dataProvider="petInfo", priority =3)
	public void deleteAPITest(int id, String name, String status) throws InterruptedException {
		System.out.println("----------------------------->> DELETE API - id : "+id);
		Response response = given()
				.spec(reqSpec)
				.header("api_key", "special-key")
				.when()
				.pathParam("petId", id)
				.log().uri()
				.delete("/{petId}");
		
		response.then().spec(resSpec);
		response.then().log().body();
		Thread.sleep(2000);
		
	}

}












