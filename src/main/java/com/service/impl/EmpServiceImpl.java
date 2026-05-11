package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Emp;
import com.mapper.EmpMapper;
import com.service.EmpService;
import org.springframework.stereotype.Service;

@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements EmpService {
}