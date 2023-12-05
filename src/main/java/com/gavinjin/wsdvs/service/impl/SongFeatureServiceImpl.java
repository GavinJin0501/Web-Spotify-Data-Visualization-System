package com.gavinjin.wsdvs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gavinjin.wsdvs.mapper.SongFeatureMapper;
import com.gavinjin.wsdvs.model.domain.SongFeature;
import com.gavinjin.wsdvs.service.SongFeatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class SongFeatureServiceImpl extends ServiceImpl<SongFeatureMapper, SongFeature>
        implements SongFeatureService {
    @Resource
    private SongFeatureMapper songFeatureMapper;


    @Override
    public List<String> findUnavailableSongs(String tableName) {
        return songFeatureMapper.findUnavailableSongs(tableName);
    }
}
