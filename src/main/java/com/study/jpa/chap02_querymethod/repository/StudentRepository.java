package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {

    // 컬럼으로 쓰지말고, 엔터티 클래스 필드명으로 써야함!
    // sql 물음표에 들어갈 것을 파라미터로 넣기
    // 동명이인이 있을 수 도 있으니 list로 받기
    List<Student> findByName(String name);


    List<Student> findByCityAndMajor(String city, String major);


    // like 쿼리
    List<Student> findByMajorContaining(String major);


    // 네이티브 쿼리 사용 가능 (실무에서 최적화 용도로 사용함)
    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :nm", nativeQuery = true)
    Student findNameWithSQL(@Param("nm")String name);
//    @Query(value = "SELECT * FROM tbl_student WHERE stu_name = :name")
//    Student findNameWithSQL(String name);


}
