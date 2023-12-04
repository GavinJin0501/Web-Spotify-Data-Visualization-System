package com.gavinjin.wsdvs.model.domain;

import lombok.Data;

@Data
public class PlaylistSong {
    private String playlistName;

    private String trackName;

    private String artistName;

    private String albumName;

    private String trackUri;

    private String addedDate;
}
