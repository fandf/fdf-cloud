package com.fdf.entity;

import lombok.Data;

/**
 * @author fandongfeng
 * @date 2022/11/24 10:28
 */
@Data
public class ReqInfoEntity {

    /**
     * 服务ip
     */
    private String serviceAddress;
    /**
     * 服务名称
     */
    private String serviceName;

}
