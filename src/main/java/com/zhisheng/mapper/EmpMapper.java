package com.zhisheng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhisheng.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpMapper extends BaseMapper<Emp> {
}