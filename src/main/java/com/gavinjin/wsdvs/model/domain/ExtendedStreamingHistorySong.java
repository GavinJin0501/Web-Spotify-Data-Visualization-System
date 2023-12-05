package com.gavinjin.wsdvs.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ExtendedStreamingHistorySong {
    @TableField(value = "track_uri")
    private String trackUri;

    private String timestamp;

    @TableField(value = "ms_played")
    private Long millisecondsPlayed;
}
