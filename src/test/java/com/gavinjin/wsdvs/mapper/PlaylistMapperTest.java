package com.gavinjin.wsdvs.mapper;

import com.gavinjin.wsdvs.model.domain.PlaylistSong;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaylistMapperTest {
    @Resource
    private PlaylistMapper playlistMapper;

    private static final String tableName = "playlists_1725026668730429441";

    @Test
    void dropPreviousPlaylistTable() {
        System.out.println(playlistMapper.dropPreviousPlaylistTable(tableName));
    }

    @Test
    void createNewPlaylistTable() {
        System.out.println(playlistMapper.createNewPlaylistTable(tableName));
    }

    @Test
    void insertAllSongs() {
        List<PlaylistSong> songs = generateSongs();

        playlistMapper.insertAllSongs(tableName, songs);
    }

    private List<PlaylistSong> generateSongs() {
        List<PlaylistSong> songs = new ArrayList<>();
        PlaylistSong song1 = new PlaylistSong();
        song1.setPlaylistName("ggg");
        PlaylistSong song2 = new PlaylistSong();

        songs.add(song1);
        songs.add(song2);
        return songs;
    }
}