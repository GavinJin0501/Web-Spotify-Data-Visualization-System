package com.gavinjin.wsdvs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import com.gavinjin.wsdvs.model.vo.SecPlayedByDayVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * SQL operations on history table
 */
public interface ExtendedStreamingHistoryMapper extends BaseMapper<ExtendedStreamingHistorySong> {
    @Update("DROP TABLE IF EXISTS ${tableName}")
    void dropPreviousExtendedStreamingHistoryTable(@NotNull String tableName);

    void createNewExtendedStreamingHistoryTable(@NotNull String tableName);

    int insertAllHistories(@NotNull String tableName, @NotNull List<ExtendedStreamingHistorySong> list);

    List<SecPlayedByDayVO> getSecPlayedByDay(@NotNull String tableName);

    @Select("SELECT SUM(sec_played) FROM ${tableName} WHERE HOUR(CONVERT_TZ(timestamp, '+00:00', '-05:00')) >= ${from} AND HOUR(CONVERT_TZ(timestamp, '+00:00', '-05:00')) < ${to}")
    Long getSecPlayedByPeriods(@NotNull String tableName, @NotNull Integer from, @NotNull Integer to);

    @Select("SELECT COUNT(*) FROM ${tableName}")
    Long getTotalListenedSongs(String tableName);
}
