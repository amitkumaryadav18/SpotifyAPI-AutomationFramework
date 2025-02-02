package com.spotify.api;

import com.spotify.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;


public class TokenManager {

    private static String access_token;
    private static Instant expiry_time;

    public synchronized static String getToken(){
        try {
            if(access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token... ");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSec = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSec - 300);
            }
            else{
                System.out.println("Token is good to use!");
            }

        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("ABORT!! Failed to get token! ");
        }
        return access_token;
    }

    private static Response renewToken(){
        HashMap<String, String > formParams = new HashMap<>();
        formParams.put("client_id", ConfigLoader.getInstance().secretConfig.client_id());
        formParams.put("client_secret",ConfigLoader.getInstance().secretConfig.client_secret());
        formParams.put("refresh_token",ConfigLoader.getInstance().secretConfig.refresh_token());
        formParams.put("grant_type",ConfigLoader.getInstance().configProperties.grantType());

        Response response = RestResource.postAccount(formParams);

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT! Renew token failed");
        }
        return response;
    }
}
