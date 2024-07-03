package com.spotify.tests;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Error;
import com.spotify.pojo.Playlist;
import com.spotify.utils.DataLoader;
import com.spotify.utils.FakerUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest{

    @Story("Create a playlist story")
    @Description("This is the description")
    @TmsLink("342")
    @Test(description = "Should be able to create playlist")
    public void shouldBeAbleCreateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(requestPlaylist);

        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        // Test

        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

    }

    @Test(description = "Should be able to get playlist")
    public void shouldBeAbleToGetAPlaylist(){

        Playlist requestPlaylist = playlistBuilder("New TestPlaylist","New playlist description",true);

        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);

        assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);
    }

    @Test(description = "Should be able to update a playlist")
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(),requestPlaylist);

        assertStatusCode(response.statusCode(),  StatusCode.CODE_200);
    }

    @Story("Create a playlist story")
    @Test(description = "Should NOT be able to create playlist without name")
    public void shouldBeNotAbleCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("",FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(requestPlaylist);

        assertStatusCode(response.statusCode(),  StatusCode.CODE_400);

        Error error = response.as(Error.class);

        assertError(error, StatusCode.CODE_400);

    }

    @Story("Create a playlist story")
    @Test(description = "Should NOT be able to create playlist with expiredToken")
    public void shouldBeNotAbleCreateAPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.post(invalidToken,requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        Error error = response.as(Error.class);

        assertError(error, StatusCode.CODE_401);

    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode expectedStatusCode){
        assertThat(actualStatusCode,equalTo(expectedStatusCode.getCode()));
    }

    @Step
    public void assertError(Error responseError, StatusCode expectedStatusCode){
        assertThat(responseError.getError().getStatus(), equalTo(expectedStatusCode.getCode()));
        assertThat(responseError.getError().getMessage(), equalTo(expectedStatusCode.getMsg()));
    }
}
