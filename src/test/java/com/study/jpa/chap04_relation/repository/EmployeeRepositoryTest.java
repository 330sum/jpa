package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import com.study.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @BeforeEach
    void bulkInsert() {
        Department d1 = Department.builder()
                .name("영업부")
                .build();

        Department d2 = Department.builder()
                .name("개발부")
                .build();

        departmentRepository.save(d1);
        departmentRepository.save(d2);

        Employee e1 = Employee.builder()
                .name("라이옹")
                .department(d1) // 엔터티자체를 넣어줌!
                .build();

        Employee e2 = Employee.builder()
                .name("어피치")
                .department(d1) // 엔터티자체를 넣어줌!
                .build();

        Employee e3 = Employee.builder()
                .name("프로도")
                .department(d2) // 엔터티자체를 넣어줌!
                .build();

        Employee e4 = Employee.builder()
                .name("네오")
                .department(d2) // 엔터티자체를 넣어줌!
                .build();

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);

    }


    @Test
    @DisplayName("특정사원의 정보조회")
    void testFindOne() {
        //given
        Long id = 2L;

        //when
//        Optional<Employee> employee = employeeRepository.findById(id);
        Employee employee = employeeRepository
                            .findById(id)
                            .orElseThrow(
                                    () -> new RuntimeException("사원없음")
                            );

        //then
//        employee.ifPresent(emp -> {
//            System.out.println("emp = " + emp);
//        });
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("\n\n\n");

        assertEquals("어피치", employee.getName());

        // JPA가 성능을 위해서 select 안 날리고, 메모리에 있는것을 가져다 씀

        // 사원을 다가져와야하니까, 부서에 null이 있다고 생각하고,
        // 사원을 left에 두고 left join 함
    }

    @Test
    @DisplayName("부서정보 조회")
    void testFindDept() {
        //given
        Long id = 1L;
        //when
//        Optional<Department> department = departmentRepository.findById(id);
        Department department = departmentRepository
                                .findById(id)
                                .orElseThrow();

        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("\n\n\n");

    }



}