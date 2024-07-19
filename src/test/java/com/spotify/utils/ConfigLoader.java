package com.spotify.utils;

import org.aeonbits.owner.ConfigFactory;

public class ConfigLoader {

    private static ConfigLoader configLoader;

    public SecretsLoader secretConfig;
    public ConfigProperties configProperties;
    public DataProperties dataProperties;

    private ConfigLoader(){
        secretConfig = ConfigFactory.create(SecretsLoader.class);
        configProperties = ConfigFactory.create(ConfigProperties.class);
        dataProperties = ConfigFactory.create(DataProperties.class);
        System.out.println("Config: "+ secretConfig.user_id());
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }
}
