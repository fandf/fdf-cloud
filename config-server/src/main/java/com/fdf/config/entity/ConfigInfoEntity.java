package com.fdf.config.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("config_info")
public class ConfigInfoEntity extends Model<ConfigInfoEntity> {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("`key`")
    private String key;
    @TableField("`value`")
    private String value;
    private Long configId;
}
