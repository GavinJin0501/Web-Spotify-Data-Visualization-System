package com.gavinjin.wsdvs.general;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import com.gavinjin.wsdvs.model.domain.SpotifyUserData;
import com.gavinjin.wsdvs.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class TestFileOperations {
    @Resource
    private UserService userService;

    @Test
    public void testReadUserAddress() {
        String fileName = "./src/main/resources/spotify_data/UserAddress.json";
        String json = userService.convertUserAddressToString(fileName);
        JSONArray objects = JSONUtil.parseArray(json);
        for (Object each : objects) {
            System.out.println(each);
        }
    }

    @Test
    public void testConvertJsonToObject() {
        String fileName = "./src/main/resources/spotify_data/Userdata.json";
        JSON json = JSONUtil.readJSON(new File(fileName), StandardCharsets.UTF_8);
        SpotifyUserData spotifyUserData = json.toBean(SpotifyUserData.class);
        System.out.println(spotifyUserData);
    }

    @Test
    public void testEmptyJsonToBean() {
        String json = "{}";
        SpotifyUserData spotifyUserData = JSONUtil.toBean(json, SpotifyUserData.class);
        System.out.println(spotifyUserData);
    }

    @Test
    public void testReadLargeJsonUsingString() throws Exception {
        long start = System.currentTimeMillis();

        String fileName = "src/main/resources/spotify_data/extending streaming history/Streaming_History_Audio_2021-2023.json";

        JSONArray jsonArray = JSONUtil.parseArray(JSONUtil.readJSON(new File(fileName), StandardCharsets.UTF_8));
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ExtendedStreamingHistorySong song = new ExtendedStreamingHistorySong();
            song.setTrackUri(jsonObject.get("spotify_track_uri", String.class));
            song.setTimestamp(jsonObject.get("ts", String.class));
            song.setMillisecondsPlayed(jsonObject.get("ms_played", Long.class));
        }
        System.out.println(jsonArray.size());

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    @Test
    public void testReadLargeJsonUsingStream() throws Exception {
        long start = System.currentTimeMillis();

        String fileName = "src/main/resources/spotify_data/extending streaming history/Streaming_History_Audio_2021-2023.json";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonFactory jsonFactory = objectMapper.getFactory();
        JsonParser jsonParser = jsonFactory.createParser(new File(fileName));

        if (jsonParser.nextToken() == JsonToken.START_ARRAY) {
            int i = 0;
            while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
                JsonNode jsonObject = objectMapper.readTree(jsonParser);

                ExtendedStreamingHistorySong song = new ExtendedStreamingHistorySong();
                song.setTrackUri(jsonObject.get("spotify_track_uri").asText());
                song.setTimestamp(jsonObject.get("ts").asText());
                song.setMillisecondsPlayed(jsonObject.get("ms_played").asLong());

                i++;
            }
            System.out.println(i);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
