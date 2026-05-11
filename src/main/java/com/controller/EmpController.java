package com.controller;

import com.entity.Emp;
import com.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 查询全部员工
     */
    @GetMapping
    public List<Emp> list() {

        return empService.list();
    }

    /**
     * 根据 id 查询员工
     */
    @GetMapping("/{id}")
    public Emp getById(@PathVariable Long id) {

        return empService.getById(id);
    }

    /**
     * 新增员工
     */
    @PostMapping
    public String add(@RequestBody Emp emp) {
        emp.setCreateTime(LocalDateTime.now());

        boolean result = empService.save(emp);

        return result ? "新增成功" : "新增失败";
    }

    /**
     * 根据 id 修改员工工资
     */
    @PutMapping("/{id}/salary")
    public String updateSalary(@PathVariable Long id,
                               @RequestParam BigDecimal salary) {
        Emp emp = new Emp();
        emp.setId(id);
        emp.setSalary(salary);

        boolean result = empService.updateById(emp);

        return result ? "修改成功" : "修改失败";
    }

    /**
     * 根据 id 删除员工
     */
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id) {
        boolean result = empService.removeById(id);

        return result ? "删除成功" : "删除失败";
    }
}