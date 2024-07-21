package com.spotify.tests.playlist;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Error;
import com.spotify.pojo.Playlist;
import com.spotify.tests.BaseTest;
import com.spotify.utils.ConfigLoader;
import com.spotify.utils.playlists.PlaylistUtils;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetPlaylistDetailTests extends BaseTest {

    private final PlaylistUtils playlistUtils = new PlaylistUtils();


    @TmsLink("110")
    @Test(description = "Should be able to get playlist details")
    public void shouldBeAbleToGetAPlaylist(){

        Playlist requestPlaylist = playlistUtils.playlistBuilder("New TestPlaylist","New playlist description",true);

        Response response = PlaylistApi.get(ConfigLoader.getInstance().dataProperties.getPlaylistId());
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_200);

        playlistUtils.assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @TmsLink("111")
    @Test(description = "Should be able to filter the Get playlist details using field param")
    public void shouldBeAbleToFilterGetAPlaylist(){

        playlistUtils.playlistBuilder("New TestPlaylist","New playlist description",true);

        Map<String, Object> reqParams = new HashMap<>();
        // TODO: to check if can create a seperate json file for data -> "New testplaylist" such that it has fixed tracks and other details..
        reqParams.put("fields", "tracks(items())");
        Response response = PlaylistApi.get(ConfigLoader.getInstance().dataProperties.getPlaylistId(),reqParams);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_200);

    }

    @TmsLink("112")
    @Test(description = "Should NOT be able to get playlist details if invalid token is passed")
    public void shouldNotBeAbleToGetAPlaylistDetailsForInvalidToken(){
        String invalidToken = "21234";

        Response response = PlaylistApi.get(ConfigLoader.getInstance().secretConfig.user_id(), invalidToken);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_401);
    }

}
