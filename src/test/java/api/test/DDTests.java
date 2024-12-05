package api.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.playload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String UserID, String UserName, String FirstName, String LastName, String Email, String Password, String Phone) {
		
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(UserID));
		userPayload.setUsername(UserName);
		userPayload.setFirstName(FirstName);
		userPayload.setLastName(LastName);
		userPayload.setEmail(Email);
		userPayload.setPassword(Password);
		userPayload.setPhone(Phone);
		
		Response response=UserEndPoints.createUser(userPayload);
		AssertJUnit.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testReadUserByName(String userName) {
		Response response=UserEndPoints.readUser(userName);
		response.then().log().all();
		AssertJUnit.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName) {
		
		Response response=UserEndPoints.deleteUser(userName);
		AssertJUnit.assertEquals(response.getStatusCode(),200);
	
	}

}
