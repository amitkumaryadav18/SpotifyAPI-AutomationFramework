package com.spotify.tests.playlist;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Error;
import com.spotify.pojo.Playlist;
import com.spotify.tests.BaseTest;
import com.spotify.utils.FakerUtils;
import com.spotify.utils.playlists.PlaylistUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class CreatePlaylistTests extends BaseTest {

    private final PlaylistUtils playlistUtils = new PlaylistUtils();

    @Description("This is the description")
    @TmsLink("101")
    @Test(description = "Should be able to create playlist")
    public void shouldBeAbleCreateAPlaylist(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);

        playlistUtils.assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }


    @TmsLink("102")
    @Test(description = "Should NOT be able to create playlist without name")
    public void shouldBeNotAbleCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder("",FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(),  StatusCode.CODE_400);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_400);

    }

    @TmsLink("103")
    @Story("Create a playlist story")
    @Test(description = "Should NOT be able to create playlist with expiredToken")
    public void shouldBeNotAbleCreateAPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(invalidToken,requestPlaylist);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_401);

    }

    @TmsLink("104")
    @Test(description = "Should be able to create multiple playlist with same name")
    public void shouldBeAbleCreateMultiplePlaylistsWithSameName(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);

        playlistUtils.assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

        Response responseTwo = PlaylistApi.post(requestPlaylist);

        playlistUtils.assertStatusCode(responseTwo.statusCode(), StatusCode.CODE_201);

        playlistUtils.assertPlaylistEqual(responseTwo.as(Playlist.class), requestPlaylist);
    }

    @TmsLink("105")
    @Test(description = "Should NOT be able to create playlist for another user")
    public void shouldNotBeAbleCreatePlaylistForAnotherUser(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.postWithAnotherUserId(requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_403);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_403);
    }
}
