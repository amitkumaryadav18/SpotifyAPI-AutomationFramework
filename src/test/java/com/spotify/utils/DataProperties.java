package com.spotify.utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/data.properties"}
)
public interface DataProperties extends Config {
    @Key("get_playlist_id")
    String getPlaylistId();

    @Key("update_playlist_id")
    String getUpdatePlaylistId();

    @Key("add_uri_1")
    String getAddUriOne();

    @Key("add_uri_2")
    String getAddUriTwo();
}