package com.study.jpa.chap03_pagination.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPageRepository extends JpaRepository<Student, String> {

    // 학생 전체조회 (조건 없음 + 페이징)
    Page<Student> findAll(Pageable pageable); // 조건없이 페이징은 안만들어도 됨 (기본기능)

    // 학생 전체조회 (조건 있음 - 학생이름에 특정단어 포함 + 페이징 (즉, 검색 + 페이징) )
    Page<Student> findByNameContaining(String name, Pageable pageable);



}
