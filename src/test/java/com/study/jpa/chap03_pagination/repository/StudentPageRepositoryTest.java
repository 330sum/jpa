package com.study.jpa.chap03_pagination.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 만약에 서비스 클래스를 사용한다면 서비스 클래스에 @Transactional 붙일것!
@Transactional // JPA는 필수임 (I, U, D시에 반드시 트랜잭션 처리가 필수)
@Rollback(false)
class StudentPageRepositoryTest {

    @Autowired
    StudentPageRepository studentPageRepository;

    @BeforeEach
    void bulkInsert() {
        // 학생을 147명 저장
        for (int i = 1; i <= 147 ; i++) {
            Student s = Student.builder()
                    .name("이자바" + i)
                    .city("뉴욕" + i)
                    .major("컴공" + i)
                    .build();
            studentPageRepository.save(s);
        }
    }


    @Test
    @DisplayName("기본 페이징 테스트")
    void testBasicPagination() {
        //given
        int pageNo = 15;
        int amount = 10;
        // 1번 페이지에서 10개 보여줘
        // 원래 페이지 DTO는 필요함 (귀찮으니까 지금 이렇게 한거임)

        // 페이지 정보 생성
//        PageRequest pageInfo = PageRequest.of(pageNo, amount);
        // 인터페이스로 구현하고 있어서 Pageable로 가능
        Pageable pageInfo = PageRequest.of(pageNo -1, amount, Sort.by("name").descending());
        /*
            *** pageNo -1
            주의사항 : 페이지번호가 zero-based
            예) 1페이지는 0, 13페이지는 12
        */
        /*
            *** Sort.by("name").descending()
            정렬기준 : 필드명으로 작성 (디비 컬럼명 아님!!!!!!)
            스트링으로 문자 비교하기 때문에 (뉴욕99 > 뉴욕147)
        */

        //when
        Page<Student> students = studentPageRepository.findAll(pageInfo);

        // 페이징 완료된 데이터셋
        // 페이지 가져올 때 무조건 getContent()
        List<Student> studentList = students.getContent();
        // 총 페이지 수
        int totalPages = students.getTotalPages();
        // 총 학생 수
        long totalElements = students.getTotalElements();
        // 이전페이지로, 다음페이지로 갈 수 있음?
        Pageable prev = students.getPageable().previousOrFirst();
        Pageable next = students.getPageable().next();
        boolean b = students.getPageable().hasPrevious();
        boolean paged = students.getPageable().isPaged();

        //then
        System.out.println("\n\n\n");
        System.out.println("totalPages = " + totalPages);
        System.out.println("totalElements = " + totalElements);
        System.out.println("prev = " + prev);
        System.out.println("next = " + next);
        System.out.println("b = " + b);
        System.out.println("paged = " + paged);
        System.out.println("\n\n\n");
        studentList.forEach(System.out::println);
        System.out.println("\n\n\n");
    }


}