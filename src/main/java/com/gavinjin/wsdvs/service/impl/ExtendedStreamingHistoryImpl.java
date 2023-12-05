package com.gavinjin.wsdvs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavinjin.wsdvs.mapper.ExtendedStreamingHistoryMapper;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import com.gavinjin.wsdvs.service.ExtendedStreamingHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ExtendedStreamingHistoryImpl extends ServiceImpl<ExtendedStreamingHistoryMapper, ExtendedStreamingHistorySong>
        implements ExtendedStreamingHistoryService {
    @Resource
    private ExtendedStreamingHistoryMapper extendedStreamingHistoryMapper;


    @Override
    public void dropPreviousExtendedStreamingHistoryTable(String tableName) {
        extendedStreamingHistoryMapper.dropPreviousExtendedStreamingHistoryTable(tableName);
    }

    @Override
    public void createNewExtendedStreamingHistoryTable(String tableName) {
        extendedStreamingHistoryMapper.createNewExtendedStreamingHistoryTable(tableName);
    }

    @Override
    public int insertAllHistories(String tableName, List<ExtendedStreamingHistorySong> list) {
        return extendedStreamingHistoryMapper.insertAllHistories(tableName, list);
    }
}
