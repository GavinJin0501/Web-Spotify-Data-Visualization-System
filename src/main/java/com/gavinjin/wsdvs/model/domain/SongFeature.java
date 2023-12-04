package com.gavinjin.wsdvs.model.domain;

import lombok.Data;

@Data
public class SongFeature {
    private String trackUri;
    private Double danceability;
    private Double energy;
    private Double loudness;
    private Double liveness;
    private Double valence;
    private Double tempo;
}