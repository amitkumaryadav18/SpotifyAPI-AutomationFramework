package com.spotify.api.applicationApi;

import com.spotify.api.RestResource;
import com.spotify.api.TokenManager;
import com.spotify.pojo.Playlist;
import com.spotify.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.api.Route.PLAYLISTS;
import static com.spotify.api.Route.USERS;

public class PlaylistApi {

    //static String access_token = "BQBQpQf4tUTFVOx8EjiJZ5cIJEF69FjibUYIDFLhALzD3Ri3LPWbluLrv_yC5GmcNArVmGAB3aBJaUbbvhMHdvRm3qX3RCuMjksCPSw2XiVHu1Ps0-xfb6bOfj1QCLawysDaQabDbJmHRLLL3bBb_Diq_sjgjYX5c2grit0nyVRXp_CnubPatCjf9dTA3kIawq-dzEHOg5N8fr4kJyW0iffn1hY4l5cNygqZLDSysL9Fg7cWaTNqIueSan0F4R7SwS90cW7AKKqQgd5c";

    @Step
    public static Response post(Playlist requestPlaylist){
       return RestResource.post(USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS,
               TokenManager.getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(USERS + "/"  + ConfigLoader.getInstance().getUserId()+ PLAYLISTS,
                token, requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" + playlistId, TokenManager.getToken());

    }

    public static Response update( String playlistId, Playlist requestPlaylist){
        return RestResource.update(PLAYLISTS + "/" + playlistId, TokenManager.getToken(), requestPlaylist);

    }
}
