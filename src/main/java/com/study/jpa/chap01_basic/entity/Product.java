package com.study.jpa.chap01_basic.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "prod_id")
    private long id;

    @Column(name = "prod_nm", nullable = false, length = 30)
    private String name;

    @Builder.Default
    private int price = 0; // 기본값 0으로 (빌더 사용하는 경우 @Builder.Default 작성해줘야 함)

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreationTimestamp
    @Column(updatable = false) // 등록시간 설정되면 수정할 수 없도록 false
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;



    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }


}
