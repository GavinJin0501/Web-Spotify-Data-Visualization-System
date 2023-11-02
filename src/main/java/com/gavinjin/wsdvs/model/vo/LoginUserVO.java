package com.gavinjin.wsdvs.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * Username
     */
    private String username;
}
