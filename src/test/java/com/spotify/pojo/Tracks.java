
package com.spotify.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotify.pojo.getUserPlaylists.Item;
import com.spotify.pojo.getUserPlaylists.TrackItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Tracks {

    @JsonProperty("href")
    private String href;
    @JsonProperty("items")
    private List<TrackItem> items;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("next")
    private Object next;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("previous")
    private Object previous;
    @JsonProperty("total")
    private Integer total;


}
