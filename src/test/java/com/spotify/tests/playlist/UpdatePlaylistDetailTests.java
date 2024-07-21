package com.spotify.tests.playlist;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Error;
import com.spotify.pojo.Playlist;
import com.spotify.tests.BaseTest;
import com.spotify.utils.ConfigLoader;
import com.spotify.utils.FakerUtils;
import com.spotify.utils.playlists.PlaylistUtils;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UpdatePlaylistDetailTests extends BaseTest {

    private final PlaylistUtils playlistUtils = new PlaylistUtils();

    @TmsLink("113_1")
    @Test(description = "Should be able to update a playlist")
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        Response response = PlaylistApi.update(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(),  StatusCode.CODE_200);
    }

    @TmsLink("113")
    @Test(description = "Should be able to update a playlist with: Name only")
    public void shouldBeAbleToUpdateAPlaylistWithNameOnly(){
        Playlist requestPlaylist = Playlist.builder().name(FakerUtils.generateName()).build();

        Response response = PlaylistApi.update(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(),  StatusCode.CODE_200);
    }

    @TmsLink("114")
    @Test(description = "Should be able to update a playlist with: Description only")
    public void shouldBeAbleToUpdateAPlaylistWithDescriptionOnly(){
        Playlist requestPlaylist = Playlist.builder().description(FakerUtils.generateDescription()).build();

        Response response = PlaylistApi.update(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(),  StatusCode.CODE_200);
    }

    @TmsLink("115")
    @Test(description = "Should be able to update a playlist with: public status only")
    public void shouldBeAbleToUpdateAPlaylistWithVisibilityStatusOnly(){
        Playlist requestPlaylist = Playlist.builder()._public(true).build();

        Response response = PlaylistApi.update(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(),  StatusCode.CODE_200);
    }

    @TmsLink("116")
    @Test(description = "Should NOT be able to update playlist list")
    public void shouldNotBeAbleToUpdateAPlaylistForInvalidToken(){
        String invalidToken = "1234";
        Playlist requestPlaylist = Playlist.builder().name(FakerUtils.generateName()).build();

        Response response = PlaylistApi.update(ConfigLoader.getInstance().secretConfig.user_id(),requestPlaylist,invalidToken);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_401);
    }
}
