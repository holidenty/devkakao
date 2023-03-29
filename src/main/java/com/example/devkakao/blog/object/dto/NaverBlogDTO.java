package com.example.devkakao.blog.object.dto;

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
    List<Items> items;
    int display;
    int start;
    int total;
    String lastBuildDate;

    @Getter
    @Setter
    public static class Items {
        String postdate;
        String bloggerlink;
        String bloggername;
        String description;
        String link;
        String title;
    }
}
