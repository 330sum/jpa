package com.study.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
//@ToString
// jpa연관관계 매핑에서는 연관관계 데이터는 ToString에서 제외해야 한다 -> 자기자신 컬럼만 찍으라는 거 (남의것 찍지마)
@ToString(exclude = {"employee"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    // 객체지향스러워려면 이 부서 클래스(1)가 사원 리스트(many)를 가지고 있기
    // 양방향관계는 데이터베이스에서는 없음!!
    // 컬럼을 만드는것이 아니라, 그냥 조회용임
    // 양방향 매핑에서는 상대방 엔터티의 갱신에 관여할 수 없다
    // 단순히 읽기전용(조회)으로만 사용해야함
    // mappedBy에는 상대방 엔터티의 조인되는 필드명을 써줌
    // 반드시 초기화 필요
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();
}
