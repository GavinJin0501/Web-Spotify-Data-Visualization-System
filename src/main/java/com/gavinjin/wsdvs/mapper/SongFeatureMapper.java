package com.gavinjin.wsdvs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gavinjin.wsdvs.model.domain.SongFeature;

import java.util.List;

public interface SongFeatureMapper extends BaseMapper<SongFeature> {
    List<String> findUnavailableSongs(String tableName);
}
