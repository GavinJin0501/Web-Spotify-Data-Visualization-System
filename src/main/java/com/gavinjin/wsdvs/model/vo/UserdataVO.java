package com.gavinjin.wsdvs.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * Userdata value object to visualize
 */
@Data
public class UserdataVO {
    private String spotifyUsername;

    private String spotifyEmail;

    private String spotifyCountry;

    private Boolean spotifyCreatedFromFacebook;

    private String spotifyFacebookUid;

    private String spotifyBirthdate;

    private String spotifyGender;

    private String spotifyPostalCode;

    private String spotifyMobileNumber;

    private String spotifyMobileOperator;

    private String spotifyMobileBrand;

    private String spotifyCreateTime;

    private String spotifyAddresses;

    private String spotifyPayments;

    private String spotifyInferences;

    private Boolean playlistsReady;

    private Boolean streamingHistoryReady;
}
