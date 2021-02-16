package api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.testng.Assert;
import org.testng.TestException;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APITests {

	@Test
	public void getDetailsTest() {

		String filepath = "apitest.csv";
		String line = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				String url = values[0];
				String identifier1 = values[1];
				String value1 = values[2];
				String canrelist = values[3];
				String promotion = values[4];
				String identifier2 = values[5];
				String value2 = values[6];

				validateAcceptanceCriteria1(url, identifier1, value1);
				validateAcceptanceCriteria2(canrelist);
				validateAcceptanceCriteria3(promotion, identifier2, value2);
			}
		} catch (FileNotFoundException e) {

			throw new TestException(e.getMessage());
		} catch (IOException e) {

			throw new TestException(e.getMessage());
		}
	}

	public void validateAcceptanceCriteria1(String url, String identifier1, String value1) throws TestException {

		RestAssured.baseURI = url;
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "2");
		JsonPath Responsemsg = response.jsonPath();
		String name = Responsemsg.get(identifier1);
		Assert.assertEquals(name, value1);
	}

	public void validateAcceptanceCriteria2(String canrelist) throws TestException {

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "2");
		JsonPath Responsemsg = response.jsonPath();
		String canRelist = Responsemsg.get(canrelist);
		Assert.assertEquals(canRelist, true);

	}

	public void validateAcceptanceCriteria3(String promotion, String identifier2, String value2) throws TestException {

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "2");
		JsonPath Responsemsg = response.jsonPath();
		String promotions = Responsemsg.get(promotion);
		Assert.assertEquals(promotions.contains(identifier2), true, value2);
	}
	
}
