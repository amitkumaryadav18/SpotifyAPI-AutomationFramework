package com.spotify.tests.playlist;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Error;
import com.spotify.pojo.getUserPlaylists.UserPlaylists;
import com.spotify.tests.BaseTest;
import com.spotify.utils.ConfigLoader;
import com.spotify.utils.playlists.PlaylistUtils;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetUserPlaylistTests extends BaseTest {

    private final PlaylistUtils playlistUtils = new PlaylistUtils();


    @TmsLink("106")
    @Test(description = "Should be able to get user playlist list")
    public void shouldBeAbleToGetAPlaylist(){
        Map<String, Object> reqParams = new HashMap<>();
        Response response = PlaylistApi.getUserPlaylists(ConfigLoader.getInstance().secretConfig.user_id(),reqParams);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_200);

        playlistUtils.assertUserPlaylistListResponse(response.as(UserPlaylists.class));
    }

    @TmsLink("107")
    @Test(description = "Should NOT be able to get playlist list")
    public void shouldNotBeAbleToGetAPlaylistForInvalidToken(){
        String invalidToken = "1234";

        Response response = PlaylistApi.getUserPlaylists(ConfigLoader.getInstance().secretConfig.user_id(), invalidToken);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_401);
    }

    @TmsLink("108")
    @Test(description = "Should be able to get user playlist list with limit param")
    public void shouldBeAbleToGetAPlaylistByPassingLimit(){
        Map<String, Object> reqParams = new HashMap<>();
        int limitSize = 1;
        reqParams.put("limit", limitSize);
        Response response = PlaylistApi.getUserPlaylists(ConfigLoader.getInstance().secretConfig.user_id(), reqParams);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_200);

        playlistUtils.assertUserPlaylistListResponseOfSize(response.as(UserPlaylists.class), limitSize);
    }

    @TmsLink("109")
    @Test(description = "Verify that limit param as Zero is not allowed in get user playlist list with limit param")
    public void shouldNotBeAbleToGetAPlaylistByPassingLimitAsZero(){
        Map<String, Object> reqParams = new HashMap<>();
        int limitSize = 0;
        reqParams.put("limit", limitSize);
        Response response = PlaylistApi.getUserPlaylists(ConfigLoader.getInstance().secretConfig.user_id(), reqParams);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_400_INVALID_LIMIT);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_400_INVALID_LIMIT);

    }
}
