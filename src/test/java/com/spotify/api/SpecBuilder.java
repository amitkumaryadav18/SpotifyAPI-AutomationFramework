package com.spotify.api;

import com.spotify.utils.ConfigLoader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.spotify.api.Route.BASE_PATH;

public class SpecBuilder {


    public static RequestSpecification getRequestSpec(){
        System.out.println("BaseURI :"+ ConfigLoader.getInstance().configProperties.baseURI()); // Or System.getProperty("BASE_URI")
        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().configProperties.baseURI()).
                //setBaseUri("https://api.spotify.com").
                setBasePath(BASE_PATH).
                setContentType(ContentType.JSON).
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getAccountRequestSpec(){
        System.out.println("ACCOUNT BaseURI :"+ ConfigLoader.getInstance().configProperties.accountsBaseURI());
        return new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getInstance().configProperties.accountsBaseURI()).
                //setBaseUri("https://accounts.spotify.com").
                setContentType(ContentType.URLENC).
                addFilter(new AllureRestAssured()).
                log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder().
                log(LogDetail.ALL).
                build();
    }
}
