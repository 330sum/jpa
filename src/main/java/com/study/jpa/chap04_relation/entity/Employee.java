package com.study.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter
@ToString @EqualsAndHashCode(of = "id")
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
    @ManyToOne
    @JoinColumn(name = "dept_id") // FK 적어주기
    private Department department;
    // 사원 조회하는 경우 join 쿼리가 나감

}
