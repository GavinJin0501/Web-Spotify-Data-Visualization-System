package com.gavinjin.wsdvs.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SpotifyApi {
    private static final String TRACK_INFO_URL = "https://api.spotify.com/v1/audio-features/";

    private static final String BEARER_URL = "https://accounts.spotify.com/api/token";

    private static String SPOTIFY_BEARER = "";

    @Value("${user-service.spotify-client-id}")
    private String spotifyClientId;

    @Value("${user-service.spotify-client-secret}")
    private String spotifyClientSecret;

    // An hour in milliseconds
    private static final long HOUR = 1000 * 60 * 59;

    public String getSpotifyBearer() {
        return SPOTIFY_BEARER;
    }

    /**
     * Request the spotify for a new bearer when the bean is injected
     */
    @PostConstruct
    public void initializeBearer() {
        requestBearerFromSpotify();
    }

    @Scheduled(fixedRate = HOUR, initialDelay = HOUR)
    public void periodicallyUpdateBearer() {
        requestBearerFromSpotify();
    }

    public void requestBearerFromSpotify() {
        // grant_type=client_credentials&client_id=your-client-id&client_secret=your-client-secret
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("grant_type", "client_credentials");
        parameterMap.put("client_id", spotifyClientId);
        parameterMap.put("client_secret", spotifyClientSecret);
        String formData = HttpUtil.toParams(parameterMap);

        String response = HttpRequest.post(BEARER_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(formData)
                .execute()
                .body();

        SPOTIFY_BEARER = JSONUtil.parse(response).getByPath("access_token", String.class);
    }

    public String getTrackInfo(String trackId) {
        return HttpRequest.get(TRACK_INFO_URL + trackId)
                .header("Authorization", "Bearer " + SPOTIFY_BEARER)
                .execute()
                .body();
    }

    public String getTracksInfo(List<String> trackIds) {
        if (trackIds.size() == 0) {
            return null;
        }
        String requestIds = String.join(",", trackIds);
        return HttpRequest.get(TRACK_INFO_URL)
                .header("Authorization", "Bearer " + SPOTIFY_BEARER)
                .body("ids=" + requestIds)
                .execute()
                .body();
    }
}
