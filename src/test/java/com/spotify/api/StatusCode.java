package com.spotify.api;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201,""),
    CODE_400(400,"Missing required field: name"),
    CODE_401(401,"Invalid access token"),
    CODE_403(403,"You cannot create a playlist for another user"),
    CODE_400_INVALID_LIMIT(400,"Invalid limit");

    private final int code;
    private final String msg;

    StatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
