package com.spotify.pojo.getUserPlaylists;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackDetail {
    @JsonProperty("preview_url")
    public String previewUrl;
    @JsonProperty("available_markets")
    public List<String> availableMarkets;
    @JsonProperty("explicit")
    public Boolean explicit;
    @JsonProperty("type")
    public String type;
    @JsonProperty("episode")
    public Boolean episode;
    @JsonProperty("track")
    public Boolean track;
    @JsonProperty("album")
    public Object album;
    @JsonProperty("artists")
    public List<Object> artists;
    @JsonProperty("disc_number")
    public Integer discNumber;
    @JsonProperty("track_number")
    public Integer trackNumber;
    @JsonProperty("duration_ms")
    public Integer durationMs;
    @JsonProperty("external_ids")
    public Object externalIds;
    @JsonProperty("external_urls")
    public Object externalUrls;
    @JsonProperty("href")
    public String href;
    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("popularity")
    public Integer popularity;
    @JsonProperty("uri")
    public String uri;
    @JsonProperty("is_local")
    public Boolean isLocal;

}
