package com.fdf.config.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fdf.config.entity.ConfigInfoEntity;
import com.fdf.config.mapper.ConfigInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/11/27 22:03
 */
@Service
public class ConfigInfoService extends ServiceImpl<ConfigInfoMapper, ConfigInfoEntity> {

    public List<ConfigInfoEntity> listByConfigId(Long configId) {
        LambdaQueryWrapper<ConfigInfoEntity> wrap = new LambdaQueryWrapper<>();
        wrap.eq(ConfigInfoEntity::getConfigId, configId);
        return list(wrap);
    }
}
