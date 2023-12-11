package com.gavinjin.wsdvs.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavinjin.wsdvs.api.SpotifyApi;
import com.gavinjin.wsdvs.mapper.PlaylistMapper;
import com.gavinjin.wsdvs.model.domain.PlaylistSong;
import com.gavinjin.wsdvs.model.domain.SongFeature;
import com.gavinjin.wsdvs.model.domain.User;
import com.gavinjin.wsdvs.model.vo.ArtistFeaturesVO;
import com.gavinjin.wsdvs.model.vo.PlaylistFeaturesVO;
import com.gavinjin.wsdvs.service.PlaylistService;
import com.gavinjin.wsdvs.service.SongFeatureService;
import com.gavinjin.wsdvs.service.UserService;
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

    @Resource
    private SongFeatureService songFeatureService;

    @Resource
    private UserService userService;

    @Resource
    private SpotifyApi spotifyApi;

    private static final int BATCH_SIZE = 512;

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
                // System.out.println(playlistName + ": " + items.size());
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
                    if (songs.size() == BATCH_SIZE) {
                        playlistMapper.insertAllSongs(tableName, songs);
                        songs.clear();
                    }

                    if (songs.size() > 0) {
                        playlistMapper.insertAllSongs(tableName, songs);
                        songs.clear();
                    }
                }

            }

            // Start to request the Spotify api to aggregate song data
            List<String> songs = songFeatureService.findUnavailableSongs(tableName);
            int n = songs.size();
            for (int i = 0; i < n; i += 100) {
                String response = spotifyApi.getTracksInfo(songs.subList(i, Math.min(n, i + 100)));
                JSONArray jsonArray = JSONUtil.parseArray(JSONUtil.parseObj(response).get("audio_features"));
                List<SongFeature> features = new ArrayList<>();

                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    SongFeature songFeature = new SongFeature();
                    songFeature.setTrackUri(jsonObject.get("uri", String.class));
                    songFeature.setDanceability(jsonObject.get("danceability", Double.class));
                    songFeature.setEnergy(jsonObject.get("energy", Double.class));
                    songFeature.setLoudness(jsonObject.get("loudness", Double.class));
                    songFeature.setLiveness(jsonObject.get("liveness", Double.class));
                    songFeature.setValence(jsonObject.get("valence", Double.class));
                    songFeature.setTempo(jsonObject.get("tempo", Double.class));
                    features.add(songFeature);
                }

                songFeatureService.saveOrUpdateBatch(features);
            }

            User user = userService.getById(userId);
            user.setPlaylistsReady(true);
            userService.updateById(user);

        }, threadPoolExecutor);
    }

    @Override
    public List<PlaylistFeaturesVO> getPlaylistFeatures(String tableName) {
        return playlistMapper.getPlaylistFeatures(tableName);
    }

    @Override
    public List<ArtistFeaturesVO> getArtistFeatures(String tableName) {
        return playlistMapper.getArtistFeatures(tableName);
    }

}
