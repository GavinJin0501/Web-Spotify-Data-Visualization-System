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

    private static final String tableName = "playlists_1731867749885267969";

    @Test
    void getArtistFeatures() {
        System.out.println(playlistMapper.getArtistFeatures(tableName));
    }

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

        for (int i = 0; i < 500; i++) {
            PlaylistSong song = new PlaylistSong();
            song.setPlaylistName("ggg" + i);
            song.setTrackUri("hahaha" + i);
            songs.add(song);
        }

        return songs;
    }

    @Test
    void getPlaylistFeatures() {
        String tableName = "playlists_1731867749885267969";
        System.out.println(playlistMapper.getPlaylistFeatures(tableName));
    }
}