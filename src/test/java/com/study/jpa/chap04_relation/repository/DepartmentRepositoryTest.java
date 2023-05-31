package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import com.study.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    @DisplayName("특정 부서를 조회하면 해당 부서원들도 함께 조회되어야 한다")
    void testFindDept() {
        //given
        Long id = 2L;
        //when
        Department department = departmentRepository.findById(id)
                .orElseThrow();
        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("department.getEmployees() = " + department.getEmployees());
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("Lazy로딩과 Eager로딩의 차이")
    void testLazyAndEager() {
        // 3번 사원을 조회하고 싶은데 굳이 부서정보는 필요없다. (조인필요없음)
        // 근데 jpa가 조인함 -> Eager로딩 (기본값임)
        // 그래서 LAZY로 바꿔줘야함
        //given
        Long id = 3L;
        //when
        Employee employee = employeeRepository.findById(id)
                .orElseThrow();

        //then
        System.out.println("\n\n\n");
//        System.out.println("employee = " + employee);
        // 명시적으로
        System.out.println("employee.getDepartment() = " + employee.getDepartment());
        System.out.println("\n\n\n");
    }



}