package com.gavinjin.wsdvs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gavinjin.wsdvs.model.domain.SongFeature;

import java.util.List;

public interface SongFeatureService extends IService<SongFeature> {
    List<String> findUnavailableSongs(java.lang.String tableName);
}
