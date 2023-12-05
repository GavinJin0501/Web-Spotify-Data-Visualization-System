package com.gavinjin.wsdvs.api;

import cn.hutool.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpotifyApi {
    // TODO: Need to fetch every 1 hour
    private static final String SPOTIFY_BEARER = "BQADxpMP5dY3NohBEPo7NDnsS-n5LFQacSrsyKIpwvEP1Bu5YZTJwhpvgsS9NmHLmFt3acP-2YzkB45uTOpzUp7OM23hNBNeUnGskdQtyonPDOp4mtM";

    private static final String TRACK_INFO_URL = "https://api.spotify.com/v1/audio-features/";

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
