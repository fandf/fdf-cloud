package com.fdf.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fdf.config.entity.ConfigEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:48
 */
@Repository
public interface ConfigMapper extends BaseMapper<ConfigEntity> {

}
