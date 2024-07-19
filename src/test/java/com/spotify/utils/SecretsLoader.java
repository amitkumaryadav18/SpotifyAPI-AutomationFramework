package com.spotify.utils;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:src/test/resources/secrets.properties"})
public interface SecretsLoader extends Config {
    String client_id();
    String client_secret();
    String user_id();
    String refresh_token();
}