package google.oauth2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GmailApi {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.a0AXooCgsi4YsfPJo8_umo1-qK_AgKy60fR-nHhNB5iatbkGJ5RHBu4WA3fuKeuj_roUpXCW7aM3JXYYjY4-11cSKonNhTmxCPDSjlc_zRuip3cXn3ap-2zl-Z3lBQAWH-ADEmk8ZTvsFywTkKzJKkkDI0EnQFeEo6UpPfaCgYKASISARASFQHGX2MizbSHsGENvg0KyDxbyA3ShQ0171";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com").
                addHeader("Authorization", "Bearer "+access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getUserProfile(){
        given(requestSpecification).
                basePath("gmail/v1").
                pathParam("userid","roraldagger@gmail.com").
        when().
                get("/users/{userid}/profile").
        then().spec(responseSpecification);

    }

    @Test
    public void sendMessage(){
        String msg = "From: roraldagger@gmail.com\n" +
                "To: roraldagger@gmail.com\n" +
                "Subject: Rest assured Test email\n" +
                "\n" +
                "Sending from rest assured API";
        String base64EncodedString = Base64.getUrlEncoder().encodeToString(msg.getBytes());
        HashMap<String, String> payload = new HashMap<>();
        payload.put("raw",base64EncodedString);
        given(requestSpecification).
                basePath("gmail/v1").
                pathParam("userid","roraldagger@gmail.com").
                body(payload).
            when().
                post("/users/{userid}/messages/send").
            then().spec(responseSpecification);
    }

    @Test
    public void util(){
        String msg = "Basic <base64 encoded client_id:client_secret>";
    }
}
