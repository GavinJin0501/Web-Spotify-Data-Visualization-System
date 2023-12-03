package com.gavinjin.wsdvs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gavinjin.wsdvs.model.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * SQL operations on user table
 */
public interface UserMapper extends BaseMapper<User> {
}
