package com.gavinjin.wsdvs.api;

import cn.hutool.http.HttpRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyApi {
    // TODO: Need to fetch every 1 hour
    private static String SPOTIFY_BEARER = "BQCsvK1-WeRjHrO08e1rHkwjn1rCul9BtOsyLT-tVr2LhE5E_cL8AgQ9pi5FLLdYNqEEOHwrABlPn5jgsvky0Ye0qTaOIJEJ_53YbK_VbDn2v5yj28s";

    private static final String TRACK_INFO_URL = "https://api.spotify.com/v1/audio-features/";

    public String getTrackInfo(String trackId) {
        return HttpRequest.get(TRACK_INFO_URL + trackId)
                .header("Authorization", "Bearer " + SPOTIFY_BEARER)
                .execute()
                .body();
    }

    public List<String> getTracksInfo(List<String> trackIds) {
        List<String> responses = new ArrayList<>();
        int n = trackIds.size();

        for (int i = 0; i < n; i += 100) {
            String requestIds = String.join(",", trackIds.subList(i, Math.min(i + 100, n)));
            responses.add(HttpRequest.get(TRACK_INFO_URL)
                    .header("Authorization", "Bearer " + SPOTIFY_BEARER)
                    .body("ids=" + requestIds)
                    .execute()
                    .body());
        }

        return responses;
    }
}
