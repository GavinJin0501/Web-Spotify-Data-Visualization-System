package com.gavinjin.wsdvs.model.domain;

import lombok.Data;

@Data
public class ExtendedStreamingHistorySong {
    private String trackName;

    private String artistName;

    private String albumName;

    private String trackUri;

    private String timestamp;

    private Long millisecondsPlayed;
}
