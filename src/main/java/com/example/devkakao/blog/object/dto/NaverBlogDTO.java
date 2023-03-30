package com.example.devkakao.blog.object.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
@NoArgsConstructor
@Getter
@Setter
public class NaverBlogDTO {

    String source = "NaverBlog";
    List<Items> items;
    int display;
    int start;
    int total;
    String lastBuildDate;

    @Getter
    @Setter
    public static class Items {

        @Schema(description = "페이지 번호", example = "1")
        int pageNumber;
        @Schema(description = "페이지 내 글 번호", example = "1")
        int blogNumber;
        String postdate;
        String bloggerlink;
        String bloggername;
        String description;
        String link;
        String title;
    }
}
