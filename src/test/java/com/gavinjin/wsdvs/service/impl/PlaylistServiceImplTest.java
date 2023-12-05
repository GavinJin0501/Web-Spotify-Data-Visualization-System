package com.gavinjin.wsdvs.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gavinjin.wsdvs.model.domain.SongFeature;
import com.gavinjin.wsdvs.service.SongFeatureService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PlaylistServiceImplTest {
    @Resource
    private SongFeatureService songFeatureService;

    @Test
    void parseTracksInfo() {
        String response = "{\n" +
                "  \"audio_features\" : [ {\n" +
                "    \"danceability\" : 0.938,\n" +
                "    \"energy\" : 0.405,\n" +
                "    \"key\" : 6,\n" +
                "    \"loudness\" : -6.017,\n" +
                "    \"mode\" : 1,\n" +
                "    \"speechiness\" : 0.0465,\n" +
                "    \"acousticness\" : 0.205,\n" +
                "    \"instrumentalness\" : 0.0000141,\n" +
                "    \"liveness\" : 0.124,\n" +
                "    \"valence\" : 0.799,\n" +
                "    \"tempo\" : 100.042,\n" +
                "    \"type\" : \"audio_features\",\n" +
                "    \"id\" : \"7imMaU3xinJn9QQITBDMwQ\",\n" +
                "    \"uri\" : \"spotify:track:7imMaU3xinJn9QQITBDMwQ\",\n" +
                "    \"track_href\" : \"https://api.spotify.com/v1/tracks/7imMaU3xinJn9QQITBDMwQ\",\n" +
                "    \"analysis_url\" : \"https://api.spotify.com/v1/audio-analysis/7imMaU3xinJn9QQITBDMwQ\",\n" +
                "    \"duration_ms\" : 173667,\n" +
                "    \"time_signature\" : 4\n" +
                "  }, {\n" +
                "    \"danceability\" : 0.917,\n" +
                "    \"energy\" : 0.798,\n" +
                "    \"key\" : 0,\n" +
                "    \"loudness\" : -6.907,\n" +
                "    \"mode\" : 1,\n" +
                "    \"speechiness\" : 0.0301,\n" +
                "    \"acousticness\" : 0.258,\n" +
                "    \"instrumentalness\" : 0,\n" +
                "    \"liveness\" : 0.160,\n" +
                "    \"valence\" : 0.975,\n" +
                "    \"tempo\" : 132.591,\n" +
                "    \"type\" : \"audio_features\",\n" +
                "    \"id\" : \"3Y48QiWUzLNtIFVO8QlggC\",\n" +
                "    \"uri\" : \"spotify:track:3Y48QiWUzLNtIFVO8QlggC\",\n" +
                "    \"track_href\" : \"https://api.spotify.com/v1/tracks/3Y48QiWUzLNtIFVO8QlggC\",\n" +
                "    \"analysis_url\" : \"https://api.spotify.com/v1/audio-analysis/3Y48QiWUzLNtIFVO8QlggC\",\n" +
                "    \"duration_ms\" : 173293,\n" +
                "    \"time_signature\" : 4\n" +
                "  }, {\n" +
                "    \"danceability\" : 0.922,\n" +
                "    \"energy\" : 0.909,\n" +
                "    \"key\" : 10,\n" +
                "    \"loudness\" : -2.429,\n" +
                "    \"mode\" : 0,\n" +
                "    \"speechiness\" : 0.270,\n" +
                "    \"acousticness\" : 0.0281,\n" +
                "    \"instrumentalness\" : 0,\n" +
                "    \"liveness\" : 0.0856,\n" +
                "    \"valence\" : 0.309,\n" +
                "    \"tempo\" : 95.295,\n" +
                "    \"type\" : \"audio_features\",\n" +
                "    \"id\" : \"4LwU4Vp6od3Sb08CsP99GC\",\n" +
                "    \"uri\" : \"spotify:track:4LwU4Vp6od3Sb08CsP99GC\",\n" +
                "    \"track_href\" : \"https://api.spotify.com/v1/tracks/4LwU4Vp6od3Sb08CsP99GC\",\n" +
                "    \"analysis_url\" : \"https://api.spotify.com/v1/audio-analysis/4LwU4Vp6od3Sb08CsP99GC\",\n" +
                "    \"duration_ms\" : 161507,\n" +
                "    \"time_signature\" : 4\n" +
                "  }, {\n" +
                "    \"danceability\" : 0.896,\n" +
                "    \"energy\" : 0.642,\n" +
                "    \"key\" : 0,\n" +
                "    \"loudness\" : -5.932,\n" +
                "    \"mode\" : 1,\n" +
                "    \"speechiness\" : 0.103,\n" +
                "    \"acousticness\" : 0.487,\n" +
                "    \"instrumentalness\" : 0.00000720,\n" +
                "    \"liveness\" : 0.0402,\n" +
                "    \"valence\" : 0.687,\n" +
                "    \"tempo\" : 99.037,\n" +
                "    \"type\" : \"audio_features\",\n" +
                "    \"id\" : \"5nuBZ5rw2lJa8b7uZr0kxg\",\n" +
                "    \"uri\" : \"spotify:track:5nuBZ5rw2lJa8b7uZr0kxg\",\n" +
                "    \"track_href\" : \"https://api.spotify.com/v1/tracks/5nuBZ5rw2lJa8b7uZr0kxg\",\n" +
                "    \"analysis_url\" : \"https://api.spotify.com/v1/audio-analysis/5nuBZ5rw2lJa8b7uZr0kxg\",\n" +
                "    \"duration_ms\" : 181983,\n" +
                "    \"time_signature\" : 4\n" +
                "  }]}";
        JSONArray jsonArray = JSONUtil.parseArray(JSONUtil.parseObj(response).get("audio_features"));
        List<SongFeature> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            SongFeature songFeature = new SongFeature();
            songFeature.setTrackUri(jsonObject.get("uri", String.class));
            songFeature.setDanceability(jsonObject.get("danceability", Double.class));
            songFeature.setEnergy(jsonObject.get("energy", Double.class));
            songFeature.setLoudness(jsonObject.get("loudness", Double.class));
            songFeature.setLiveness(jsonObject.get("liveness", Double.class));
            songFeature.setValence(jsonObject.get("valence", Double.class));
            songFeature.setTempo(jsonObject.get("tempo", Double.class));
            list.add(songFeature);
        }

        songFeatureService.saveOrUpdateBatch(list);
    }
}