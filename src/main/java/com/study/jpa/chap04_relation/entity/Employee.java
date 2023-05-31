package com.study.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
//@ToString
// jpa연관관계 매핑에서는 연관관계 데이터는 ToString에서 제외해야 한다 -> 자기자신 컬럼만 찍으라는 거 (남의것 찍지마)
@ToString(exclude = {"department"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Long id;

    @Column(name = "emp_name", nullable = false)
    private String name;

    // many(사원)쪽에서 1(부서)을 가진 것 (단방향관계)
    /*
        EAGER: 항상 무조건 조인을 수행 (기본값임)
        LAZY: 필요한 경우에만 조인을 수행 (실무에서 꼭!!!!!!!!!!)
    * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id") // FK 적어주기
    private Department department;
    // 사원 조회하는 경우 join 쿼리가 나감

}
