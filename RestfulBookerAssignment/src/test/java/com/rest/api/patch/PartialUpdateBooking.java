package com.rest.api.patch;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rest.api.post.CreateBooking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PartialUpdateBooking {

	private int bookingID;
	private String tokenID;

	@Test(priority = 1)
	public void Create_Booking_Test() {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		bookingID = given().log().all().contentType(ContentType.JSON)
				.body(new File(
						"C:/Users/Harshada/RestfulBookerAssignment" + "/src/test/java/resources/UserBooking.json"))
				.when().log().all().post("/booking").then().log().all().extract().path("bookingid");
	}

	@Test(priority = 2)
	public void Generate_Token_Test() {

		RestAssured.baseURI = "https://restful-booker.herokuapp.com";

		tokenID = given().log().all().contentType(ContentType.JSON)
				.body(new File("C:/Users/Harshada/Dec2022APITesting" + "/src/test/java/DataFiles/credentials.json"))
				.when().log().all().post("/auth").then().log().all().extract().path("token");

		System.out.println(tokenID);
		Assert.assertNotNull(tokenID);
	}

	
	  @Test(priority=4) 
	  public void Delete_booking_Test() 
	  {
		RestAssured
			.given().log().all()
				.baseUri("https://restful-booker.herokuapp.com/booking/"+bookingID)
				.header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
				.header("Accept","application/json")
				.header("Content-Type","application/json") 
			.when().log().all() 
				.delete()
			.then().log().all() 
				.assertThat() 
					.statusCode(201);
	  
	  }
	  
	  @Test(priority=5)
	  public void Verify_booking_is_deleted()
	  {
		  
		  RestAssured 
		  		.given().log().all()
		  			.baseUri("https://restful-booker.herokuapp.com/booking/"+bookingID)
		  		.when().log().all() 
		  				.get() 
		  		.then().log().all() 
		  			.assertThat()
		  				.statusCode(404);
	  }
	  
	 
	@Test(priority = 3)
	public void Update_partial_booking_Test() 
	{
		String jsonString = "{\r\n" + "    \"firstname\" : \"James\",\r\n" + "    \"lastname\" : \"Brown\"}";

		RestAssured.given().log().all().baseUri("https://restful-booker.herokuapp.com/booking/" + bookingID)
				.header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=").header("Accept", "application/json")
				.header("Content-Type", "application/json").body(jsonString).when().log().all().patch().then().log()
				.all().assertThat().statusCode(200).body("firstname", Matchers.equalTo("James"))
				.body("lastname", Matchers.equalTo("Brown"));

	}

}
