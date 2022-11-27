package com.fdf.config.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fdf.config.entity.ConfigEntity;
import com.fdf.config.mapper.ConfigMapper;
import org.springframework.stereotype.Service;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:49
 */
@Service
public class ConfigService extends ServiceImpl<ConfigMapper, ConfigEntity> {

    public ConfigEntity getByConfigName(String name) {
        LambdaQueryWrapper<ConfigEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConfigEntity::getName, name);
        wrapper.last("limit 1");
        return getOne(wrapper);
    }
}
