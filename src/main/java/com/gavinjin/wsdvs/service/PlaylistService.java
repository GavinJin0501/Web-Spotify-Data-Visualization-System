package com.gavinjin.wsdvs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavinjin.wsdvs.model.domain.PlaylistSong;
import com.gavinjin.wsdvs.model.vo.PlaylistFeaturesVO;

import java.util.List;

public interface PlaylistService extends IService<PlaylistSong> {
    /**
     * Save all songs in playlists
     * @param userId
     * @param content
     * @param drop
     */
    void savePlaylist(Long userId, String content, boolean drop);

    List<PlaylistFeaturesVO> getPlaylistFeatures(String tableName);
}
