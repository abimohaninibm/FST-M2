package gitProject;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;

public class GitProject {
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String ssh = "";
	public int id = 0;
	
	@BeforeClass
	public void beforeClass() {
		
		reqSpec= new RequestSpecBuilder()
				.setBaseUri("https://api.github.com")
				.setContentType(ContentType.JSON)
				.build();
		
		resSpec= new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON)
				. build();
	}
	
	@Test(priority=1)
	public void postAPITestCase() {
		System.out.println("------------------------------------------------>> POST API");
		Response response = given()
				.spec(reqSpec)
				.header("accept", "application/vnd.github.v3+json" )
				.when()
				.auth()
				.preemptive()
				.basic("token","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")
				.body("\n" + 
						"{\n" + 
						"    \"title\": \"TestAPIKey\",\n" + 
						"    \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCoWRHmhMFncEA0y5vCvXdkbNj/pNVJCHxAHt190tGsQGed0Wnao3mJ0j7qZ30bYIdIr+D2MwMRlXLHOa1yO0KGJcrUcVkzJD1+S/lvAkGAKnuF5yA77Q+o3AJiZi5BxPkyzr8MmtAErYPaEqsUgojKyt2XFgujwFXHMbpD6cUMUryFDcL4az9GS33YJ9PuvVWEUoK3dr5kIAdBxBLaVRaDtKVVJmVL1TZ3clEmxqpFyUpt+ynLVYoiOVG1qnKMqOaY++Y9tZHMcepY2qI4GKbTkHh4WCuUgTuYxUJuY3wC/znnZQLZ9I3TO7EpzwLQCx99yo+Ayk37OtM4lhgIDgSx\"\n" + 
						"}\n" + 
						"\n" + 
						"")
				.log().uri()
				.post("/user/keys");
				
		response.then().spec(resSpec);
		response.then().log().body();
		response.then().statusCode(201);
		id= response.jsonPath().getInt("id");
		System.out.println("Id of the inserted ssh public token is : "+id);
	}
	
	@Test(priority=2)
	public void getAPITestCase() {
		System.out.println("------------------------------------------------>> GET API");
		Response response = given()
				.spec(reqSpec)
				.auth()
				.preemptive()
				.basic("token","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")
				.log().uri()
				.get("/user/keys");
		
		response.then().log().body();
		response.then().statusCode(200);
		
				
	}
	
	@Test(priority=3)
	public void deleteAPITestCase() {
		System.out.println("------------------------------------------------>> DELETE API");
		Response response = given().spec(reqSpec)
				.auth()
				.preemptive()
				.basic("token","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")
				.pathParam("keyId", id)
				.when()
				.log().uri()
				.delete("/user/keys/{keyId}");
		
		response.then().log().body();
		response.then().statusCode(204);
	}
	

}
