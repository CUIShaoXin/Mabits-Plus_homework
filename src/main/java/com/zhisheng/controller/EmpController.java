package com.zhisheng.controller;

import com.zhisheng.entity.Emp;
import com.zhisheng.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 1. 保存单个员工
     */
    @PostMapping("/save")
    public String saveEmp(@RequestBody Emp emp) {
        emp.setCreateTime(LocalDateTime.now());

        boolean result = empService.save(emp);

        return result ? "保存员工成功" : "保存员工失败";
    }

    /**
     * 2. 批量保存至少 3 条员工数据
     */
    @PostMapping("/saveBatch")
    public String saveBatchEmp() {
        Emp emp1 = new Emp();
        emp1.setName("李四");
        emp1.setAge(25);
        emp1.setSalary(new BigDecimal("6500"));
        emp1.setDeptId(1L);
        emp1.setCreateTime(LocalDateTime.now());

        Emp emp2 = new Emp();
        emp2.setName("王五");
        emp2.setAge(28);
        emp2.setSalary(new BigDecimal("7200"));
        emp2.setDeptId(2L);
        emp2.setCreateTime(LocalDateTime.now());

        Emp emp3 = new Emp();
        emp3.setName("赵六");
        emp3.setAge(30);
        emp3.setSalary(new BigDecimal("8500"));
        emp3.setDeptId(3L);
        emp3.setCreateTime(LocalDateTime.now());

        List<Emp> empList = Arrays.asList(emp1, emp2, emp3);

        boolean result = empService.saveBatch(empList);

        return result ? "批量保存成功" : "批量保存失败";
    }

    /**
     * 3. 查询全部员工列表
     */
    @GetMapping("/list")
    public List<Emp> listEmp() {
        return empService.list();
    }

    /**
     * 4. 根据 id 查询员工
     */
    @GetMapping("/{id}")
    public Emp getEmpById(@PathVariable Long id) {
        return empService.getById(id);
    }

    /**
     * 5. 根据 id 修改员工工资
     */
    @PutMapping("/{id}/salary")
    public String updateSalary(@PathVariable Long id,
                               @RequestParam BigDecimal salary) {
        Emp emp = new Emp();
        emp.setId(id);
        emp.setSalary(salary);

        boolean result = empService.updateById(emp);

        return result ? "修改工资成功" : "修改工资失败";
    }

    /**
     * 6. 根据 id 修改员工年龄
     */
    @PutMapping("/{id}/age")
    public String updateAge(@PathVariable Long id,
                            @RequestParam Integer age) {
        Emp emp = new Emp();
        emp.setId(id);
        emp.setAge(age);

        boolean result = empService.updateById(emp);

        return result ? "修改年龄成功" : "修改年龄失败";
    }

    /**
     * 7. 根据 id 删除员工
     */
    @DeleteMapping("/{id}")
    public String removeEmpById(@PathVariable Long id) {
        boolean result = empService.removeById(id);

        return result ? "删除员工成功" : "删除员工失败";
    }
}