package com.gavinjin.wsdvs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavinjin.wsdvs.mapper.ExtendedStreamingHistoryMapper;
import com.gavinjin.wsdvs.model.domain.ExtendedStreamingHistorySong;
import com.gavinjin.wsdvs.model.vo.SecPlayedByDayVO;
import com.gavinjin.wsdvs.service.ExtendedStreamingHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<SecPlayedByDayVO> getSecPlayedByDay(String tableName) {
        return extendedStreamingHistoryMapper.getSecPlayedByDay(tableName);
    }

    @Override
    public Map<String, Long> getSecPlayedByPeriods(String tableName) {
        Map<String, Long> map = new HashMap<>();
        map.put("Morning", extendedStreamingHistoryMapper.getSecPlayedByPeriods(tableName, 6, 12));
        map.put("Afternoon", extendedStreamingHistoryMapper.getSecPlayedByPeriods(tableName, 12, 18));
        map.put("Evening", extendedStreamingHistoryMapper.getSecPlayedByPeriods(tableName, 18, 24));
        map.put("Night", extendedStreamingHistoryMapper.getSecPlayedByPeriods(tableName, 0, 6));
        return map;
    }

    @Override
    public Map<Integer, Long> getSecPlayedByHours(String tableName) {
        Map<Integer, Long> map = new HashMap<>();
        for (int hour = 0; hour < 24; hour++) {
            map.put(hour, extendedStreamingHistoryMapper.getSecPlayedByPeriods(tableName, hour, hour + 1));
        }
        return map;
    }
}
