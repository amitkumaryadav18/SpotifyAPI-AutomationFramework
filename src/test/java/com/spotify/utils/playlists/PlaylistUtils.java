package com.spotify.utils.playlists;

import com.spotify.api.StatusCode;
import com.spotify.pojo.Error;
import com.spotify.pojo.Playlist;
import com.spotify.pojo.getUserPlaylists.Item;
import com.spotify.pojo.getUserPlaylists.UserPlaylists;
import io.qameta.allure.Step;
import org.hamcrest.Matchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlaylistUtils {

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

    @Step
    public void assertUserPlaylistListResponse(UserPlaylists responseUserPlaylists){
        assertThat(responseUserPlaylists.getItems().size(), greaterThan(0));
        assertThat(responseUserPlaylists.getLimit(), greaterThan(0));
        assertThat(responseUserPlaylists.getItems().size(), allOf(greaterThan(0), equalTo(responseUserPlaylists.getTotal())));
    }

    @Step
    public void assertUserPlaylistListResponseContainsPlaylist(UserPlaylists responseUserPlaylists, String playlistId){
        assertThat(responseUserPlaylists.getItems(), hasItem(Matchers.<Item>hasProperty("id", equalTo(playlistId))));
    }

    @Step
    public void assertUserPlaylistListResponseOfSize(UserPlaylists responseUserPlaylists, int size){
        assertThat(responseUserPlaylists.getItems().size(), equalTo(size));
        assertThat(responseUserPlaylists.getLimit(), equalTo(size));
    }

    @Step
    public void assertPlaylistContainsUri(UserPlaylists responseUserPlaylists, String uriToMatch){
        boolean matchFound = false;
        for(Item item : responseUserPlaylists.getItems()){
            if(item.getUri().equals(uriToMatch)){
                matchFound = true;
                break;
            }
        }
        assertThat("uri not found in the list: "+ uriToMatch,matchFound);
    }

    @Step
    public void assertPlaylistContainsUriAtPosition(Playlist responseUserPlaylists, String uriToMatch, int position){

        assertThat(responseUserPlaylists.getTracks().getItems().get(position).getTrack().getUri(), equalTo(uriToMatch));
    }
}
