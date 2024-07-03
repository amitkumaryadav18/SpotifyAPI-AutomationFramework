package com.spotify.api;

import com.spotify.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.api.Route.API;
import java.util.HashMap;

import static com.spotify.api.Route.TOKEN;
import static com.spotify.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String path, String token, Object requestData){
       return given(getRequestSpec()).
                body(requestData).
               auth().oauth2(token).
            when().
                post(path).
            then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response postAccount(HashMap<String, String> formParams){
        return given().
                spec(getAccountRequestSpec()).
                formParams(formParams).
            when().
                post(API + TOKEN).
            then().spec(getResponseSpec())
                .extract().response();
    }


    public static Response get(String path, String token){
        return  given(getRequestSpec()).
                auth().oauth2(token).
            when().
                get(path).
            then().spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response update( String path, String token, Playlist reqData){
        return given(getRequestSpec()).
                body(reqData).
                auth().oauth2(token).
            when().
                put(path).
            then().spec(getResponseSpec()).
                extract().
                response();
    }
}
