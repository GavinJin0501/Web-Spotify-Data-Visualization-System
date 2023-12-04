package com.gavinjin.wsdvs.model.domain;

import lombok.Data;

@Data
public class SpotifyUserData {
    private String username;

    private String email;

    private String country;

    private Boolean createdFromFacebook;

    private String facebookUid;

    private String birthdate;

    private String gender;

    private String postalCode;

    private String mobileNumber;

    private String mobileOperator;

    private String mobileBrand;

    private String creationTime;


}
