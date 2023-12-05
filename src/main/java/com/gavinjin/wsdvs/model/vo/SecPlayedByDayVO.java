package com.gavinjin.wsdvs.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SecPlayedByDayVO implements Serializable {
    /**
     * Follow the mysql rule: Sunday is 1, Monday is 2, ...
     */
    private Integer day;
    private Long secPlayed;
}
