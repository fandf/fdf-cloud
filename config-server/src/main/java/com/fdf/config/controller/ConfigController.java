package com.fdf.config.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fdf.config.base.BaseService;
import com.fdf.config.entity.ConfigEntity;
import com.fdf.config.entity.ConfigInfoEntity;
import com.fdf.config.service.ConfigInfoService;
import com.fdf.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        JSONObject data = resultData(configEntity);
        return data != null ? setResultOk("ok", data) : setResultFail("The profile content is not configured on the server ");

    }

    private JSONObject resultData(ConfigEntity configEntity) {
        // 根据配置文件查找详细配置文件内容
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
        return data;
    }

    /**
     * md5 比对配置文件内容是否发生变化
     *
     * @param name
     * @return
     */
    @GetMapping("/md5Comparison")
    public ResponseEntity md5Comparison(String name, String md5) {
        if (StrUtil.isEmpty(name)) {
            return setResultFail("error :name is null ");
        }
        if (StrUtil.isEmpty(md5)) {
            return setResultFail("error :md5 is null ");
        }
        ConfigEntity configEntity = configService.getByConfigName(name);
        if (configEntity == null) {
            return setResultFail("The profile content is not configured on the server ");
        }
        if (configEntity.getMd5().equals(md5)) {
            return setResultOk("ok");
        }
        JSONObject data = resultData(configEntity);
        return data != null ? setResultOk("配置文件内容发生了变化", data, 201) :
                setResultFail("The profile content is not configured on the server ");
    }

    /**
     * 查询所有配置文件内容
     *
     * @return
     */
    @RequestMapping("/configList")
    public ResponseEntity configList() {
        return setResultOk("查询数据成功", configInfoService.configList());
    }

    /**
     * http://127.0.0.1:8099/updateConfig?id=1&value=fandf 修改配置文件内容
     *
     * @param id
     * @param value
     * @return
     */
    @RequestMapping("/updateConfig")
    public ResponseEntity updateConfig(Long id, String value) {
        if (id == null) {
            return setResultFail("id is null");
        }
        if (StrUtil.isEmpty(value)) {
            return setResultFail("value is null");
        }
        ConfigInfoEntity configInfo = configInfoService.getById(id);
        if (configInfo == null) {
            return setResultFail("没有查找到该配置");
        }
        configInfo.setValue(value);
        boolean update = configInfoService.updateById(configInfo);
        if (!update) {
            return setResultFail("更新失败!");
        }
        // 更新md5
        boolean updateConfig = configService.update(new LambdaUpdateWrapper<ConfigEntity>()
                .eq(ConfigEntity::getId, configInfo.getConfigId())
                .set(ConfigEntity::getMd5, System.currentTimeMillis()));
        return updateConfig ? setResultOk("更新成功", configInfoService.configList()) : setResultFail("更新失败!");
    }

}
