package com.gavinjin.wsdvs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * SQL operations on user table
 */
public interface ExtendedStreamingHistoryMapper extends BaseMapper<ExtendedStreamingHistorySong> {
    @Update("DROP TABLE IF EXISTS ${tableName}")
    void dropPreviousExtendedStreamingHistoryTable(String tableName);

    void createNewExtendedStreamingHistoryTable(String tableName);

    int insertAllHistories(String tableName, List<ExtendedStreamingHistorySong> list);
}
