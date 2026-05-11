package com.zhisheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhisheng.entity.Emp;
import com.zhisheng.mapper.EmpMapper;
import com.zhisheng.service.EmpService;
import org.springframework.stereotype.Service;

@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {
}