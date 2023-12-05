package com.gavinjin.wsdvs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import com.gavinjin.wsdvs.model.vo.SecPlayedByDayVO;

import java.util.List;
import java.util.Map;

public interface ExtendedStreamingHistoryService extends IService<ExtendedStreamingHistorySong> {
    void dropPreviousExtendedStreamingHistoryTable(String tableName);

    void createNewExtendedStreamingHistoryTable(String tableName);

    int insertAllHistories(String tableName, List<ExtendedStreamingHistorySong> list);

    List<SecPlayedByDayVO> getSecPlayedByDay(String tableName);

    Map<String, Long> getSecPlayedByPeriods(String tableName);

    Map<Integer, Long> getSecPlayedByHours(String tableName);

    Long getTotalListenedSongs(String tableName);
}
