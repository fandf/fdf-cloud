package com.fdf.config.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.fdf.config.base.BaseService;
import com.fdf.config.entity.ConfigEntity;
import com.fdf.config.entity.ConfigInfoEntity;
import com.fdf.config.service.ConfigInfoService;
import com.fdf.config.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:51
 */
@RestController
public class ConfigController extends BaseService {

    @Resource
    ConfigService configService;
    @Resource
    ConfigInfoService configInfoService;

    @GetMapping("getConfig")
    public ResponseEntity getConfig(String name) {
        if (StrUtil.isBlank(name)) {
            return setResultFail("error: name is null");
        }
        ConfigEntity configEntity = configService.getByConfigName(name);
        if (configEntity == null) {
            return setResultFail("the profile content is not configured on the server");
        }
        List<ConfigInfoEntity> configInfos = configInfoService.listByConfigId(configEntity.getId());
        if (CollUtil.isEmpty(configInfos)) {
            return null;
        }

        JSONObject data = new JSONObject();
        data.set("name", configEntity.getName());
        data.set("configId", configEntity.getId());
        data.set("md5", configEntity.getMd5());

        JSONArray jsonArray = new JSONArray();
        configInfos.forEach(d->{
            JSONObject configInfo = new JSONObject();
            configInfo.set("key", d.getKey());
            configInfo.set("value", d.getValue());
            jsonArray.add(configInfo);
        });
        data.set("config", jsonArray);
        return setResultOk("ok", data);
    }

}
