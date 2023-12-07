package com.gavinjin.wsdvs.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestSpotifyAPI {
    @Resource
    private SpotifyApi spotifyApi;

    @Test
    void getSecretKey() {
        System.out.println(spotifyApi.getSpotifyBearer());
    }

    @Test
    void getTrackInfo() {
        String trackInfo = spotifyApi.getTrackInfo("3Y48QiWUzLNtIFVO8QlggC");
        System.out.println(trackInfo);
    }

    @Test
    void getTracksInfo() {
        List<String> tracks = Arrays.asList("7imMaU3xinJn9QQITBDMwQ", "3Y48QiWUzLNtIFVO8QlggC");
        String tracksInfo = spotifyApi.getTracksInfo(tracks);
        System.out.println(tracksInfo);
    }
}
