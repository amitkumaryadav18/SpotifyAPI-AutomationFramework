package com.spotify.utils;


import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/config.properties"}
)
public interface ConfigProperties extends Config {
    String baseURI();
    String accountsBaseURI();
    String grantType();
}
