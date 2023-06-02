package com.study.jpa.chap05_practice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor(force = true) //NotNull 때문에 오류, 기본생성자에 강제로 = 0L하거나 이렇게 작성
@AllArgsConstructor
@Builder
public class PostModifyDTO {

    // 제목,내용 수정가능 (작성자 수정x)
    @NotBlank
    @Size(min = 1, max = 20)
    private String title;

    private String content;

    // 특정데이터 보내려면 PK보내야함
    // SQL 생각해보면 물음표가 3개임
    @NotNull
    private Long postNo;
//    private Long postNo=0L;

}
