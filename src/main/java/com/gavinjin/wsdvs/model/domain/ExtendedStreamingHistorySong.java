package com.gavinjin.wsdvs.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ExtendedStreamingHistorySong {
    @TableField(value = "track_uri")
    private String trackUri;

    private Timestamp timestamp;

    @TableField(value = "sec_played")
    private Integer secPlayed;
}
