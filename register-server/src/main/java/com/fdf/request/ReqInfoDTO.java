package com.fdf.request;

import lombok.Data;

/**
 * 续约请求DTO
 *
 * @author fandongfeng
 * @date 2022/11/24 17:03
 */
@Data
public class ReqInfoDTO {

    /**
     * 服务地址
     */
    private String serviceAddress;
    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * 续约时长(毫秒)  客户端传 自定义续约时间
     */
    private Long renewalDuration;

}
