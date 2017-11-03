package uk.ac.qub.csc3045.api.integration.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import uk.ac.qub.csc3045.api.model.Account;

import static io.restassured.RestAssured.given;

public class RequestHelper {
	Properties environmentProperties;

    public RequestHelper() throws IOException{
        environmentProperties = PropertiesHelper.LoadEnvironmentProperties();

        RestAssured.baseURI = "http://" + environmentProperties.getProperty("baseURI");
        RestAssured.port = Integer.parseInt(environmentProperties.getProperty("port"));
        RestAssured.basePath = environmentProperties.getProperty("basePath");
    }
    
    public String getAuthHeader(Account account) {
    	sendPostRequest("/authentication/register", account);
    	Response r = sendPostRequest("/authentication/login", account);
    	
    	return r.getHeader("Authorization");
    }

    public Response sendGetRequest(String target){
        Response r =
                given().log().everything(true).
                        when().get(target).
                        then().log().everything(true).
                        extract().response();
        return r;
    }
    
    public Response sendGetRequestWithAuthHeader(String target, String authHeader){
        Response r =
                given().log().everything(true).
                		headers("Authorization", authHeader).
                        when().get(target).
                        then().log().everything(true).
                        extract().response();
        
        return r;
    }

    public Response sendDeleteRequest(String target){
        Response r = given().log().everything(true).
                contentType(MediaType.TEXT_PLAIN_VALUE).
                when().
                delete(target).
                then().log().everything(true).
                extract().response();

        return r;
    }

    public Response sendPostRequest(String target, Object body){
        Response r  = given().log().everything(true).
                contentType("application/json").
                when().
                body(body).
                post(target).
                then().log().everything(true).
                extract().response();

        return r;
    }
    
    public Response sendPostRequestWithAuthHeader(String target, String authHeader, Object body){
        Response r  = given().log().everything(true).
                contentType("application/json").
                headers("Authorization", authHeader).
                when().
                body(body).
                post(target).
                then().log().everything(true).
                extract().response();
        
        return r;
    }
    
    public Response sendPutRequestWithAuthHeader(String target, String authHeader, Object body) {
    	Response r = given().log().everything(true).
    			contentType("application/json").
    			headers("Authorization", authHeader).
    			when().
    			body(body).
    			put(target).
    			then().log().everything(true).
    			extract().response();
    	
    	return r;
    }
}
