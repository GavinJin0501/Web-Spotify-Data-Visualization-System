package com.gavinjin.wsdvs.general;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
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
}
