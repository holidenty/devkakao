package com.example.devkakao.blog.object.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@NoArgsConstructor
@Getter
@Setter
public class SearchKeywordDTO {

    @Schema(description = "ID값, 자동 증가")
    private Long id;

    @Schema(description = "검색어")
    String target;

    @Schema(description = "검색 횟수")
    int count;
}
