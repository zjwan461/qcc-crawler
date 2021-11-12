package com.itsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@TableName("export_data")
public class ExportData {

    @TableId(type = IdType.AUTO)
    private int id;
    @TableField
    private String name;
    @TableField
    private String href;
    @TableField
    private String parent;
}
