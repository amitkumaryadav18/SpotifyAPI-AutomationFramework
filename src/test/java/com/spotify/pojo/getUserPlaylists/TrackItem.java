package com.spotify.pojo.getUserPlaylists;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackItem {

    @JsonProperty("added_at")
    public String addedAt;
    @JsonProperty("added_by")
    public Object addedBy;
    @JsonProperty("is_local")
    public Boolean isLocal;
    @JsonProperty("primary_color")
    public Object primaryColor;
    @JsonProperty("track")
    public TrackDetail track;
    @JsonProperty("video_thumbnail")
    public Object videoThumbnail;

}
