package com.gavinjin.wsdvs.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Used for mood categorization of each artist
 */
@Data
public class ArtistFeaturesVO implements Serializable {
    private String artistName;

    private Integer total;

    private Double danceability;

    private Double energy;

    private Double loudness;

    private Double liveness;

    private Double valence;

    private Double tempo;

}
