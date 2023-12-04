package com.gavinjin.wsdvs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gavinjin.wsdvs.model.domain.PlaylistSong;
import com.gavinjin.wsdvs.model.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * SQL operations on user table
 */
public interface PlaylistMapper extends BaseMapper<PlaylistSong> {
    @Update("DROP TABLE IF EXISTS ${tableName}")
    int dropPreviousPlaylistTable(String tableName);

    int createNewPlaylistTable(String tableName);

    int insertAllSongs(String tableName, List<PlaylistSong> list);
}
