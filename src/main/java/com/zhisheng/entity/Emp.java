package com.zhisheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("emp")
public class Emp {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;

    private BigDecimal salary;

    private Long deptId;

    private LocalDateTime createTime;
}