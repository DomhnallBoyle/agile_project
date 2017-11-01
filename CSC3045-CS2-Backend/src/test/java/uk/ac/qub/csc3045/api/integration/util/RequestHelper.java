package uk.ac.qub.csc3045.api.integration.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class RequestHelper {
	Properties environmentProperties;

    public RequestHelper() throws IOException{
        environmentProperties = PropertiesHelper.LoadEnvironmentProperties();

        RestAssured.baseURI = "http://" + environmentProperties.getProperty("baseURI");
        RestAssured.port = Integer.parseInt(environmentProperties.getProperty("port"));
        RestAssured.basePath = environmentProperties.getProperty("basePath");
    }

    public Response SendGetRequest(String target){
        Response r =
                given().log().everything(true).
                        when().get(target).
                        then().log().everything(true).
                        extract().response();
        return r;
    }
    
    public Response SendGetRequestWithAuthHeader(String target, String authHeader){
        Response r =
                given().log().everything(true).
                		headers("Authorization", authHeader).
                        when().get(target).
                        then().log().everything(true).
                        extract().response();
        
        return r;
    }

    public Response SendDeleteRequest(String target){
        Response r = given().log().everything(true).
                contentType(MediaType.TEXT_PLAIN_VALUE).
                when().
                delete(target).
                then().log().everything(true).
                extract().response();

        return r;
    }

    public Response SendPostRequest(String target, Object body){
        Response r  = given().log().everything(true).
                contentType("application/json").
                when().
                body(body).
                post(target).
                then().log().everything(true).
                extract().response();

        return r;
    }
    
    public Response SendPostRequestWithAuthHeader(String target, String authHeader, Object body){
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
}
