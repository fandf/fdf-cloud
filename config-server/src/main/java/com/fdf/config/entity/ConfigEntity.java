package com.fdf.config.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("config")
public class ConfigEntity extends Model<ConfigEntity> {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    /**
     * 比较内容是否发生变化
     */
    private String md5;

}
