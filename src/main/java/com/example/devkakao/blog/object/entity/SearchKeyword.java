package com.example.devkakao.blog.object.entity;

import com.example.devkakao.blog.object.dto.SearchKeywordDTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "searchkeyword")
@Entity
public class SearchKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String target;
    int count;


    @Builder
    public SearchKeyword(String target, int count){
        this.target = target;
        this.count = count;
    }

    public void update(SearchKeywordDTO searchKeywordDTO){
        this.target = searchKeywordDTO.getTarget();
        this.count = searchKeywordDTO.getCount();
    }

}
