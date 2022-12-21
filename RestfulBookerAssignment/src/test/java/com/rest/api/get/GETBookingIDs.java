package com.rest.api.get;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class GETBookingIDs {
	
	
	@Test
	public void getAPITest_Verifystatuscode()
	
	
	{
		
		// status code code changes
		Response response = 
		given().log().all()
		.when().log().all()
		.get("https://restful-booker.herokuapp.com/booking");
		
		int statusCode = response.getStatusCode();
		
		System.out.println("Response status code is : "+statusCode );
		
		Assert.assertEquals(statusCode, 200);
		
		System.out.println(response.prettyPrint());

	}
	
	@Test
	public void getAPITest_contentLength()
	{
		RestAssured.baseURI="https://restful-booker.herokuapp.com";
		given().log().all()
		.when().log().all()
			.get("/booking")
		.then().log().all()
			.assertThat()
				.statusCode(200)
		.and()
			.contentType(ContentType.JSON)
		.and()
			.header("Server", equalTo("Cowboy"));
	}
	


}
