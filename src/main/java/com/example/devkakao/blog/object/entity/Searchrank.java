package com.example.devkakao.blog.object.entity;

import com.example.devkakao.blog.object.dto.SearchrankDTO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "searchrank")
@Entity
public class Searchrank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String target;
    int count;


    @Builder
    public Searchrank(String target, int count){
        this.target = target;
        this.count = count;
    }

    public void update(SearchrankDTO searchrankDTO){
        this.target = searchrankDTO.getTarget();
        this.count = searchrankDTO.getCount();
    }

}
