package com.zhisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhisheng.entity.Emp;
import com.zhisheng.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/emps")
public class EmpController_3 {

    @Autowired
    private EmpService empService;

    /**
     * 1. 保存单个员工
     * POST http://localhost:8080/emps/save
     */
    @PostMapping("/save")
    public String saveEmp(@RequestBody Emp emp) {
        emp.setCreateTime(LocalDateTime.now());

        boolean result = empService.save(emp);

        return result ? "保存员工成功" : "保存员工失败";
    }

    /**
     * 2. 批量保存员工
     * POST http://localhost:8080/emps/saveBatch
     */
    @PostMapping("/saveBatch")
    public String saveBatchEmp() {
        Emp emp1 = new Emp();
        emp1.setName("测试员工A");
        emp1.setAge(24);
        emp1.setSalary(new BigDecimal("6000"));
        emp1.setDeptId(1L);
        emp1.setCreateTime(LocalDateTime.now());

        Emp emp2 = new Emp();
        emp2.setName("测试员工B");
        emp2.setAge(28);
        emp2.setSalary(new BigDecimal("7500"));
        emp2.setDeptId(2L);
        emp2.setCreateTime(LocalDateTime.now());

        Emp emp3 = new Emp();
        emp3.setName("测试员工C");
        emp3.setAge(32);
        emp3.setSalary(new BigDecimal("9000"));
        emp3.setDeptId(3L);
        emp3.setCreateTime(LocalDateTime.now());

        List<Emp> empList = Arrays.asList(emp1, emp2, emp3);

        boolean result = empService.saveBatch(empList);

        return result ? "批量保存成功" : "批量保存失败";
    }

    /**
     * 3. 查询全部员工
     * GET http://localhost:8080/emps/list
     */
    @GetMapping("/list")
    public List<Emp> listEmp() {
        return empService.list();
    }

    /**
     * 4. 根据 id 查询员工
     * GET http://localhost:8080/emps/2
     */
    @GetMapping("/{id}")
    public Emp getEmpById(@PathVariable Long id) {
        return empService.getById(id);
    }

    /**
     * 5. 根据 id 修改员工工资
     * PUT http://localhost:8080/emps/2/salary?salary=8888
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
     * PUT http://localhost:8080/emps/2/age?age=26
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
     * DELETE http://localhost:8080/emps/22
     */
    @DeleteMapping("/{id}")
    public String removeEmpById(@PathVariable Long id) {
        boolean result = empService.removeById(id);

        return result ? "删除员工成功" : "删除员工失败";
    }

    /**
     * 8. 根据姓名模糊查询员工
     * GET http://localhost:8080/emps/query/name?name=李
     */
    @GetMapping("/query/name")
    public List<Emp> queryByName(@RequestParam(required = false) String name) {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(name), Emp::getName, name);

        return empService.list(queryWrapper);
    }

    /**
     * 9. 查询年龄大于等于某值的员工
     * GET http://localhost:8080/emps/query/age?age=25
     */
    @GetMapping("/query/age")
    public List<Emp> queryByAge(@RequestParam(required = false) Integer age) {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.ge(age != null, Emp::getAge, age);

        return empService.list(queryWrapper);
    }

    /**
     * 10. 查询工资在某区间范围内的员工
     * GET http://localhost:8080/emps/query/salary?minSalary=6000&maxSalary=9000
     */
    @GetMapping("/query/salary")
    public List<Emp> queryBySalary(@RequestParam(required = false) BigDecimal minSalary,
                                   @RequestParam(required = false) BigDecimal maxSalary) {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.ge(minSalary != null, Emp::getSalary, minSalary)
                .le(maxSalary != null, Emp::getSalary, maxSalary);

        return empService.list(queryWrapper);
    }

    /**
     * 11. 查询指定部门下的员工
     * GET http://localhost:8080/emps/query/dept?deptId=2
     */
    @GetMapping("/query/dept")
    public List<Emp> queryByDept(@RequestParam(required = false) Long deptId) {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(deptId != null, Emp::getDeptId, deptId);

        return empService.list(queryWrapper);
    }

    /**
     * 12. 查询姓名不为空的员工
     * GET http://localhost:8080/emps/query/nameNotEmpty
     */
    @GetMapping("/query/nameNotEmpty")
    public List<Emp> queryNameNotEmpty() {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.isNotNull(Emp::getName)
                .ne(Emp::getName, "");

        return empService.list(queryWrapper);
    }

    /**
     * 13. 查询多个部门中的员工
     * GET http://localhost:8080/emps/query/depts?deptIds=1&deptIds=2&deptIds=3
     */
    @GetMapping("/query/depts")
    public List<Emp> queryByDeptIds(@RequestParam(required = false) List<Long> deptIds) {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.in(deptIds != null && !deptIds.isEmpty(), Emp::getDeptId, deptIds);

        return empService.list(queryWrapper);
    }

    /**
     * 14. 组合查询：姓名模糊匹配 + 年龄范围 + 部门筛选
     * GET http://localhost:8080/emps/query/complex?name=李&minAge=20&maxAge=30&deptId=1
     */
    @GetMapping("/query/complex")
    public List<Emp> queryComplex(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) Integer minAge,
                                  @RequestParam(required = false) Integer maxAge,
                                  @RequestParam(required = false) Long deptId) {
        LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(name), Emp::getName, name)
                .ge(minAge != null, Emp::getAge, minAge)
                .le(maxAge != null, Emp::getAge, maxAge)
                .eq(deptId != null, Emp::getDeptId, deptId);

        return empService.list(queryWrapper);
    }
}