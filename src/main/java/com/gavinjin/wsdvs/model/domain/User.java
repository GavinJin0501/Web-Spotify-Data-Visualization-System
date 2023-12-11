package com.gavinjin.wsdvs.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "user")
@Data
public class User implements Serializable {
    /**
     * Visualization program account info
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String password;

    /**
     * Spotify personal info
     */
    @TableField(value = "spotify_username", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyUsername;

    @TableField(value = "spotify_email", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyEmail;

    @TableField(value = "spotify_country", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyCountry;

    @TableField(value = "spotify_created_from_facebook", updateStrategy = FieldStrategy.IGNORED)
    private Boolean spotifyCreatedFromFacebook;

    @TableField(value = "spotify_facebook_uid", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyFacebookUid;

    @TableField(value = "spotify_birthdate", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyBirthdate;

    @TableField(value = "spotify_gender", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyGender;

    @TableField(value = "spotify_postal_code", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyPostalCode;

    @TableField(value = "spotify_mobile_number", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyMobileNumber;

    @TableField(value = "spotify_mobile_operator", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyMobileOperator;

    @TableField(value = "spotify_mobile_brand", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyMobileBrand;

    @TableField(value = "spotify_create_time", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyCreateTime;


    @TableField(value = "spotify_addresses", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyAddresses;

    @TableField(value = "spotify_payments", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyPayments;

    @TableField(value = "spotify_inferences", updateStrategy = FieldStrategy.IGNORED)
    private String spotifyInferences;

    @TableField("playlists_ready")
    private Boolean playlistsReady;
}