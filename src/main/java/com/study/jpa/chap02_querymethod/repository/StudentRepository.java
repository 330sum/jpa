package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


    /* JPQL
        SELECT 별칭 FROM 엔터티클래스명 as 별칭 WHERE 별칭.필드명 = ? (as 생략 가능)

        native-sql : SELECT * FROM tbl_student WHERE stu_name = ?
        jpql :       SELECT st FROM Student AS st WHERE st.name = ?  (as 생략 가능)
    * */

    // 도시 이름으로 학생 조회
//    @Query(value = "SELECT * FROM tbl_student WHERE city = ?1", nativeQuery = true)
    @Query("SELECT s FROM Student s WHERE s.city = ?1")
    Student getByCityWithJPQL(String city);
    // ?1 ?2 이런것 보다 @Param으로 지정해주는게 실무에서 더 좋은방법
    // unique가 아니면 list로 받는게 맞음! 현재 테스트 용도로 이렇게 쓰는공!


    @Query("SELECT st FROM Student st WHERE st.name LIKE %:nm%")
    List<Student> searchByNamesWithJPQL(@Param("nm")String name);


    /* JPQL로 수정 삭제 쿼리 쓰기 */
    @Modifying /* 조회가 아닐 경우, @Modifying 무조건 붙여야 함 */
    @Query("DELETE FROM Student s WHERE s.name = ?1")
    void deleteByNameWithJPQL(String name);


}
