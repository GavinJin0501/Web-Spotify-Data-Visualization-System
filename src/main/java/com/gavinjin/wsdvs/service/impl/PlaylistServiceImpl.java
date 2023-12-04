package com.gavinjin.wsdvs.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavinjin.wsdvs.mapper.PlaylistMapper;
import com.gavinjin.wsdvs.model.domain.PlaylistSong;
import com.gavinjin.wsdvs.service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import static com.gavinjin.wsdvs.utils.constant.UserConstant.DB_TABLE_PLAYLISTS;

@Service
// @Slf4j
public class PlaylistServiceImpl extends ServiceImpl<PlaylistMapper, PlaylistSong>
        implements PlaylistService {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private PlaylistMapper playlistMapper;

    @Override
    public void savePlaylist(Long userId, String content, boolean drop) {
        CompletableFuture.runAsync(() -> {
            final String tableName = DB_TABLE_PLAYLISTS + userId;

            // If it is the first request, drop the previous playlists table and create a new one
            if (drop) {
                System.out.println(playlistMapper.dropPreviousPlaylistTable(tableName));
                System.out.println(playlistMapper.createNewPlaylistTable(tableName));
            }

            JSON json = JSONUtil.parse(content);
            JSONArray playlists = JSONUtil.parseArray(json.getByPath("playlists", String.class));
            for (int i = 0; i < playlists.size(); i++) {
                JSONObject playlist = playlists.getJSONObject(i);
                String playlistName = playlist.get("name", String.class);
                List<PlaylistSong> songs = new ArrayList<>();

                JSONArray items = JSONUtil.parseArray(playlist.get("items"));
                for (int j = 0; j < items.size(); j++) {
                    JSONObject item = items.getJSONObject(j);

                    PlaylistSong song = new PlaylistSong();
                    song.setPlaylistName(playlistName);
                    song.setTrackName(item.getByPath("track.trackName", String.class));
                    song.setArtistName(item.getByPath("track.artistName", String.class));
                    song.setAlbumName(item.getByPath("track.albumName", String.class));
                    song.setTrackUri(item.getByPath("track.trackUri", String.class));
                    song.setAddedDate(item.get("addedDate", String.class));

                    songs.add(song);
                }
                playlistMapper.insertAllSongs(tableName, songs);
            }

            // Start to request the JEN api to aggregate song data
        }, threadPoolExecutor);
    }

}
