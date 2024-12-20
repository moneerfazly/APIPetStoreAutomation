package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.playload.User;
import io.restassured.response.Response;

public class UserTest2 {
	
	Faker faker;
	User userPayload;
	public Logger logger; 
	
	@BeforeClass
	public void setUp() {
		
		
		faker =new Faker();
		userPayload =new User();
		
		userPayload.setId(faker.hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Logs
		
		logger=LogManager.getLogger(this.getClass());
		logger.debug("debuggin..............");
		
	}
	
	@Test(priority=1)
	public void testPostUser(){
		
		logger.info("************** Creating User *****************");
		
		Response response=UserEndPoints2.createUser(userPayload);
		
		response.then().log().all();
		AssertJUnit.assertEquals(response.getStatusCode(),200);
		
		logger.info("************** User Is Created *****************");

		
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		
		logger.info("************** Reading User *****************");
		
		Response response=UserEndPoints2.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		AssertJUnit.assertEquals(response.getStatusCode(),200);	
		
		logger.info("************** User Info Is Displayed *****************");

	
	}
	
	
	@Test(priority=3)
	public void testUpdatetUserByName() {
		
		logger.info("************** Updating User *****************");

		
		//Update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response=UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		
		response.then().log().body();
		AssertJUnit.assertEquals(response.getStatusCode(),200);
		
		logger.info("************** User is Updating *****************");

		//Checking data after update
		Response responseAfterUpdate=UserEndPoints2.readUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(responseAfterUpdate.getStatusCode(),200);
		

			
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		logger.info("************** Deleting User *****************");

		Response response=UserEndPoints2.deleteUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(response.getStatusCode(),200);
		
		logger.info("************** User Is Deleted *****************");

		
	}

}
