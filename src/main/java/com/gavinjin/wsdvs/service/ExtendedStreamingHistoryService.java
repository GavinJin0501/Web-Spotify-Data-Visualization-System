package com.gavinjin.wsdvs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;

import java.util.List;

public interface ExtendedStreamingHistoryService extends IService<ExtendedStreamingHistorySong> {
    void dropPreviousExtendedStreamingHistoryTable(String tableName);

    void createNewExtendedStreamingHistoryTable(String tableName);

    int insertAllHistories(String tableName, List<ExtendedStreamingHistorySong> list);
}
