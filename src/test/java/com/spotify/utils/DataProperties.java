package com.spotify.utils;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/config.properties"}
)
public interface DataProperties extends Config {
    @Key("get_playlist_id")
    String getPlaylistId();

    @Key("update_playlist_id")
    String getUpdatePlaylistId();
}