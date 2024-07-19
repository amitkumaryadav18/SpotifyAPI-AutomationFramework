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

    @Step
    public static Response post(Playlist requestPlaylist){
        System.out.println(ConfigLoader.getInstance().secretConfig);
       return RestResource.post(USERS + "/" + ConfigLoader.getInstance().secretConfig.user_id() + PLAYLISTS,
               TokenManager.getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(USERS + "/"  + ConfigLoader.getInstance().secretConfig.user_id()+ PLAYLISTS,
                token, requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" + playlistId, TokenManager.getToken());

    }

    public static Response update( String playlistId, Playlist requestPlaylist){
        return RestResource.update(PLAYLISTS + "/" + playlistId, TokenManager.getToken(), requestPlaylist);

    }
}
