package com.zhisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhisheng.entity.Emp;
import com.zhisheng.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 1. 员工分页查询
     * GET http://localhost:8080/emps/page?current=1&size=5
     */
    @GetMapping("/page")
    public IPage<Emp> pageEmp(@RequestParam(defaultValue = "1") Long current,
                              @RequestParam(defaultValue = "5") Long size) {

        Page<Emp> page = new Page<>(current, size);

        return empService.page(page);
    }

    /**
     * 2. 分页 + 按工资排序
     * GET http://localhost:8080/emps/page/salary?current=1&size=5&order=asc
     * GET http://localhost:8080/emps/page/salary?current=1&size=5&order=desc
     */
    @GetMapping("/page/salary")
    public IPage<Emp> pageOrderBySalary(@RequestParam(defaultValue = "1") Long current,
                                        @RequestParam(defaultValue = "5") Long size,
                                        @RequestParam(defaultValue = "asc") String order) {

        Page<Emp> page = new Page<>(current, size);

        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        if ("desc".equalsIgnoreCase(order)) {
            queryWrapper.orderByDesc(Emp::getSalary);
        } else {
            queryWrapper.orderByAsc(Emp::getSalary);
        }

        return empService.page(page, queryWrapper);
    }

    /**
     * 3. 分页 + 按创建时间倒序
     * GET http://localhost:8080/emps/page/createTime?current=1&size=5
     */
    @GetMapping("/page/createTime")
    public IPage<Emp> pageOrderByCreateTime(@RequestParam(defaultValue = "1") Long current,
                                            @RequestParam(defaultValue = "5") Long size) {

        Page<Emp> page = new Page<>(current, size);

        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Emp::getCreateTime);

        return empService.page(page, queryWrapper);
    }

    /**
     * 4. 投影查询：只查询 id、name、salary
     * GET http://localhost:8080/emps/page/fields?current=1&size=5
     */
    @GetMapping("/page/fields")
    public IPage<Map<String, Object>> pageSelectFields(@RequestParam(defaultValue = "1") Long current,
                                                       @RequestParam(defaultValue = "5") Long size) {

        Page<Map<String, Object>> page = new Page<>(current, size);

        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "salary");

        return empService.pageMaps(page, queryWrapper);
    }

    /**
     * 5. 组合查询：部门筛选 + 分页 + 工资排序 + 只返回部分字段
     * GET http://localhost:8080/emps/page/dept?deptId=2&current=1&size=5
     */
    @GetMapping("/page/dept")
    public IPage<Map<String, Object>> pageByDeptAndSalary(@RequestParam(required = false) Long deptId,
                                                          @RequestParam(defaultValue = "1") Long current,
                                                          @RequestParam(defaultValue = "5") Long size) {

        Page<Map<String, Object>> page = new Page<>(current, size);

        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();

        queryWrapper.select("id", "name", "salary")
                    .eq(deptId != null, "dept_id", deptId)
                    .orderByAsc("salary");

        return empService.pageMaps(page, queryWrapper);
    }
}