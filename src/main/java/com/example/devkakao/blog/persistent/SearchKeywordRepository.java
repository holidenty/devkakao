package com.example.devkakao.blog.persistent;

import com.example.devkakao.blog.object.entity.SearchKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeyword, String> {

    Page<SearchKeyword> findAll(Pageable pageable);
    Optional<SearchKeyword> findByTarget(String query);
}
