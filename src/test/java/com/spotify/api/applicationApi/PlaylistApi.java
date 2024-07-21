package com.spotify.api.applicationApi;

import com.spotify.api.RestResource;
import com.spotify.api.TokenManager;
import com.spotify.pojo.Playlist;
import com.spotify.pojo.addTracks.AddTrackRequest;
import com.spotify.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static com.spotify.api.Route.PLAYLISTS;
import static com.spotify.api.Route.USERS;

public class PlaylistApi {

    @Step
    public static Response post(Playlist requestPlaylist){
        System.out.println(ConfigLoader.getInstance().secretConfig);
       return RestResource.post(USERS + "/" + ConfigLoader.getInstance().secretConfig.user_id() + PLAYLISTS,
               TokenManager.getToken(), requestPlaylist);
    }

    @Step
    public static Response postWithAnotherUserId(Playlist requestPlaylist){
        System.out.println(ConfigLoader.getInstance().secretConfig);
        return RestResource.post(USERS + "/" + "smedjan" + PLAYLISTS,
                TokenManager.getToken(), requestPlaylist);
    }

    public static Response post(String token, Playlist requestPlaylist){
        return RestResource.post(USERS + "/"  + ConfigLoader.getInstance().secretConfig.user_id()+ PLAYLISTS,
                token, requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(PLAYLISTS + "/" + playlistId, TokenManager.getToken());

    }
    public static Response get(String playlistId, Map<String, Object> params){
        return RestResource.getWithParams(PLAYLISTS + "/" + playlistId, TokenManager.getToken(),params);

    }
    public static Response get(String playlistId,String token){
        return RestResource.get(PLAYLISTS + "/" + playlistId, token);

    }

    public static Response getUserPlaylists(String userId, Map<String, Object> params){
        return RestResource.getWithParams(USERS + "/" + userId + "/playlists", TokenManager.getToken(),params);

    }
    public static Response getUserPlaylists(String userId, String token){
        return RestResource.get(USERS + "/" + userId + "/playlists", token);

    }

    public static Response update( String playlistId, Playlist requestPlaylist){
        return RestResource.update(PLAYLISTS + "/" + playlistId, TokenManager.getToken(), requestPlaylist);
    }

    public static Response update( String playlistId, Playlist requestPlaylist, String token){
        return RestResource.update(PLAYLISTS + "/" + playlistId, token, requestPlaylist);
    }

    public static Response addTrack(String playlistId, AddTrackRequest request){
        return RestResource.post(PLAYLISTS + "/" + playlistId + "/tracks", TokenManager.getToken(),request);
    }
    public static Response addTrack(String playlistId, AddTrackRequest request, String token){
        return RestResource.post(PLAYLISTS + "/" + playlistId + "/tracks", token,request);
    }
}
