package com.spotify.tests.playlist;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Error;
import com.spotify.pojo.Playlist;
import com.spotify.pojo.addTracks.AddTrackRequest;
import com.spotify.tests.BaseTest;
import com.spotify.utils.ConfigLoader;
import com.spotify.utils.playlists.PlaylistUtils;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;

public class AddTrackToPlaylistTests extends BaseTest {

    private final PlaylistUtils playlistUtils = new PlaylistUtils();

    @TmsLink("117")
    @Test(description = "Should be able to add a track to playlist")
    public void shouldBeAbleToAddTrackToAPlaylist(){
        AddTrackRequest request = AddTrackRequest.builder().
                uris(Arrays.asList(ConfigLoader.getInstance().dataProperties.getAddUriOne())).
                build();

        Response response = PlaylistApi.addTrack(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),request);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);

    }

    @TmsLink("119")
    @Test(description = "Should be able to add multiple tracks to playlist at once")
    public void shouldBeAbleToAddMultipleTracksToAPlaylist(){
        AddTrackRequest request = AddTrackRequest.builder().
                uris(Arrays.asList(ConfigLoader.getInstance().dataProperties.getAddUriOne(),
                        ConfigLoader.getInstance().dataProperties.getAddUriTwo())).
                build();

        Response response = PlaylistApi.addTrack(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),request);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);
    }

    @TmsLink("118")
    @Test(description = "Should be able to add tracks at particular order/ position to playlist")
    public void shouldBeAbleToAddTrackAtPositionToAPlaylist(){
        String uriToAdd = ConfigLoader.getInstance().dataProperties.getAddUriOne();
        int position = 0;
        AddTrackRequest request = AddTrackRequest.builder().
                uris(Arrays.asList(uriToAdd)).
                position(position).
                build();

        Response response = PlaylistApi.addTrack(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),request);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);

        // get the playlist details and verify that it's first in the list...
        Response getPlaylistResponse = PlaylistApi.get(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId());
        playlistUtils.assertStatusCode(getPlaylistResponse.statusCode(), StatusCode.CODE_200);

        playlistUtils.assertPlaylistContainsUriAtPosition(getPlaylistResponse.as(Playlist.class), uriToAdd, position);
    }

    @TmsLink("120")
    @Test(description = "Should NOT be able to add tracks to playlist if invalid token")
    public void shouldNotBeAbleToAddTrackToAPlaylistForInvalidToken(){
        String invalidToken = "1234";
        AddTrackRequest request = AddTrackRequest.builder().
                uris(Arrays.asList(ConfigLoader.getInstance().dataProperties.getAddUriOne())).
                build();

        Response response = PlaylistApi.addTrack(ConfigLoader.getInstance().dataProperties.getUpdatePlaylistId(),request,invalidToken);
        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_401);

        Error error = response.as(Error.class);

        playlistUtils.assertError(error, StatusCode.CODE_401);
    }
}
