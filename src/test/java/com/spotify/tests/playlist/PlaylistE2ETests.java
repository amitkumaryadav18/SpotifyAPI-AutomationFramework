package com.spotify.tests.playlist;

import com.spotify.api.StatusCode;
import com.spotify.api.applicationApi.PlaylistApi;
import com.spotify.pojo.Playlist;
import com.spotify.pojo.addTracks.AddTrackRequest;
import com.spotify.pojo.getUserPlaylists.UserPlaylists;
import com.spotify.tests.BaseTest;
import com.spotify.utils.ConfigLoader;
import com.spotify.utils.FakerUtils;
import com.spotify.utils.playlists.PlaylistUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistE2ETests extends BaseTest {

    private final PlaylistUtils playlistUtils = new PlaylistUtils();

    @TmsLink("121")
    @Test(description = "Should be able to create and add tracks to a playlist")
    public void shouldBeAbleCreateAndAddTacksToAPlaylist(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),true);

        // Create playlist
        Response response = PlaylistApi.post(requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        playlistUtils.assertPlaylistEqual(response.as(Playlist.class), requestPlaylist);

        // Add track to it
        String uriToAdd = ConfigLoader.getInstance().dataProperties.getAddUriOne();
        AddTrackRequest request = AddTrackRequest.builder().
                uris(Arrays.asList(uriToAdd)).
                build();

        Response addTrackResponse = PlaylistApi.addTrack(response.as(Playlist.class).getId(),request);
        playlistUtils.assertStatusCode(addTrackResponse.statusCode(), StatusCode.CODE_201);

        // Verify the details by fetching it through get call
        Response getPlaylistResponse = PlaylistApi.get(response.as(Playlist.class).getId());
        playlistUtils.assertStatusCode(getPlaylistResponse.statusCode(), StatusCode.CODE_200);

        playlistUtils.assertPlaylistEqual(getPlaylistResponse.as(Playlist.class), requestPlaylist);
        // verify the track is added
        playlistUtils.assertPlaylistContainsUriAtPosition(getPlaylistResponse.as(Playlist.class), uriToAdd, 0);

    }


    @TmsLink("122")
    @Test(description = "Verify that created playlist appears in the list of playlists for the user")
    public void shouldBeAbleCreateAndFindInUserList(){
        Playlist requestPlaylist = playlistUtils.playlistBuilder(FakerUtils.generateName(),FakerUtils.generateDescription(),false);

        // Create playlist
        Response response = PlaylistApi.post(requestPlaylist);

        playlistUtils.assertStatusCode(response.statusCode(), StatusCode.CODE_201);

        Playlist responseOnCreate = response.as(Playlist.class);
        playlistUtils.assertPlaylistEqual(responseOnCreate, requestPlaylist);

        // Verify the details by fetching it through get call
        Map<String, Object> reqParams = new HashMap<>();
        Response getUserPlaylistsResponse = PlaylistApi.getUserPlaylists(ConfigLoader.getInstance().secretConfig.user_id(),reqParams);
        playlistUtils.assertStatusCode(getUserPlaylistsResponse.statusCode(), StatusCode.CODE_200);

        playlistUtils.assertUserPlaylistListResponseContainsPlaylist(getUserPlaylistsResponse.as(UserPlaylists.class),responseOnCreate.getId());


    }
}
