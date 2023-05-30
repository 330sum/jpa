package com.study.jpa.chap02_querymethod.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter // 실무적 측면에서 setter는 신중히 만들 것 (setter에 의해서 객체 불변성이 깨지기 쉽기 때문에)
@Getter
@ToString @EqualsAndHashCode(of = {"id"}) // of에 기입한 것만 같으면 똑같은 객체라고 생각하겠다라는 의미
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_student")
public class Student {

    // 실무에서는 랜덤문자로 pk를 두는 경우가 더 많음
    @Id
    @Column(name = "stu_id")
    @GeneratedValue(generator = "uid")
    @GenericGenerator(strategy = "uuid", name = "uid")
    private String id;

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;

    private String major;



}
