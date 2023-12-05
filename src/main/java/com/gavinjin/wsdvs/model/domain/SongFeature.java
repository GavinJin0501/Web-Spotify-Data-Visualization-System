package com.gavinjin.wsdvs.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("song_features")
@Data
public class SongFeature {
    @TableId(type = IdType.INPUT, value = "track_uri")
    private String trackUri;

    private Double danceability;

    private Double energy;

    private Double loudness;

    private Double liveness;

    private Double valence;

    private Double tempo;
}