package com.study.jpa.chap05_practice.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PageDTO { // requestDTO (세터, 노아그 필수)

    private int page;
    private int size;

    public PageDTO() {
        this.page = 1;
        this.size = 10;
    }
}
