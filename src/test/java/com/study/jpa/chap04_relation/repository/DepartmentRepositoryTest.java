package com.study.jpa.chap04_relation.repository;

import com.study.jpa.chap04_relation.entity.Department;
import com.study.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
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
        // 3번 사원을 조회하고 싶은데 굳이 부서정보는 필요없다.
        //given
        Long id = 3L;
        //when
        Employee employee = employeeRepository.findById(id)
                .orElseThrow();

        //then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("employee.getDepartment() = " + employee.getDepartment());
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("양방향 연관관계에서 연관데이터의 수정")
    void testChangeDept() {
        // 3번사원의 부서를 2번부서에 1번부서로 변경해야 한다.
        //given
        Employee foundEmp = employeeRepository.findById(3L)
                .orElseThrow();

        Department newDept = departmentRepository.findById(1L)
                .orElseThrow();

        // 사원쪽에서 부서만 변경하는게 아니라
        foundEmp.setDepartment(newDept);
//        newDept.getEmployees().add(foundEmp);

        employeeRepository.save(foundEmp);
        //when

        // 1번 부서 정보를 조회해서 모든 사원을 보겠다.
        Department foundDept = departmentRepository.findById(1L)
                .orElseThrow();

        System.out.println("\n\n\n");

        foundDept.getEmployees().forEach(System.out::println);

        System.out.println("\n\n\n");

        //then
    }


    @Test
    @DisplayName("N+1 문제 발생 예시")
    void testNPlus1Ex() {
        // 부서별로 모든 사원 정보 조회 (조인필요 -> 근데 안함-_-)
        // 부서 2개면 쿼리가 3개 나감. 조인하면 빠른데, 쿼리가 3개임. (부서정보조회 1개, 사원 - 1번부서 1개, 사원 - 2번부서 1개)
        //given
        List<Department> departments = departmentRepository.findAll();
        //when
        departments.forEach(dept -> {
            System.out.println("\n\n======== 사원 리스트 ========\n\n");

            List<Employee> employees = dept.getEmployees();
            System.out.println(employees);

            System.out.println("\n\n============================\n\n");
        });

        //then
    }


    @Test
    @DisplayName("N+1 문제 해결 예시")
    void testNPlus1Solution() {
        // fetch join 사용하면 쿼리문이 1개 나감
        // 근데, cross join 되서 1번 부서가 여러번 나옴. 그래서 JPQL에 DISTINCT 추가하기!
        // 나중에 JPADSL? 사용하면 JPQL말고 자바코드로 작성할 수 있음
        // 페이징 조회는 fetch join이 성능이 확실히 좋음. 근데, 그외는 N+1이 더 성능이 좋을 수 도 있음
        //given
        List<Department> departments = departmentRepository.findAllIncludeEmployees();
        //when
        departments.forEach(dept -> {
            System.out.println("\n\n======== 사원 리스트 ========\n\n");

            List<Employee> employees = dept.getEmployees();
            System.out.println(employees);

            System.out.println("\n\n============================\n\n");
        });

        //then
    }


}