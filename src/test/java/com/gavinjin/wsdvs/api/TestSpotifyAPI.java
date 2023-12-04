package com.gavinjin.wsdvs.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
public class TestSpotifyAPI {
    @Resource
    private SpotifyApi spotifyApi;

    private static String SECRET_KEY = "BQCsvK1-WeRjHrO08e1rHkwjn1rCul9BtOsyLT-tVr2LhE5E_cL8AgQ9pi5FLLdYNqEEOHwrABlPn5jgsvky0Ye0qTaOIJEJ_53YbK_VbDn2v5yj28s";

    // @Test
    void getSecretKey() {
        // grant_type=client_credentials&client_id=your-client-id&client_secret=your-client-secret
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("grant_type", "client_credentials");
        parameterMap.put("client_id", "5c3909f7bf1b47fc8c027c86a266250c");
        parameterMap.put("client_secret", "3d235141b6924940b7cffa5161e923ce");
        String formData = HttpUtil.toParams(parameterMap);

        String url = "https://accounts.spotify.com/api/token";
        String response = HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(formData)
                .execute()
                .body();

        SECRET_KEY = JSONUtil.parse(response).getByPath("access_token", String.class);
        System.out.println(SECRET_KEY);
    }

    @Test
    void getTrackInfo() {
        String trackInfo = spotifyApi.getTrackInfo("3Y48QiWUzLNtIFVO8QlggC");
        System.out.println(trackInfo);
    }

    @Test
    void getTracksInfo() {
        List<String> tracks = Arrays.asList("7imMaU3xinJn9QQITBDMwQ", "3Y48QiWUzLNtIFVO8QlggC");
        List<String> tracksInfo = spotifyApi.getTracksInfo(tracks);
        System.out.println(tracksInfo);
    }
}
