package com.fdf.config.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fdf.config.entity.ConfigInfoEntity;
import com.fdf.config.entity.ConfigUnionEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:48
 */
@Repository
public interface ConfigInfoMapper extends BaseMapper<ConfigInfoEntity> {

    @Select("select a.id,b.name,a.key,a.value  from config_info  as a INNER JOIN  config as b on  a.config_id =b.id")
    List<ConfigUnionEntity> configList();
}
