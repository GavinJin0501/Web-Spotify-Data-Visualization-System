package com.gavinjin.wsdvs.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlaylistFeaturesVO implements Serializable {
    private String playlistName;

    private Double danceability;

    private Double energy;

    private Double loudness;

    private Double liveness;

    private Double valence;

    private Double tempo;

}
