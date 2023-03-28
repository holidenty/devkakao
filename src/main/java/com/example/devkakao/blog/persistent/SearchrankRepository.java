package com.example.devkakao.blog.persistent;

import com.example.devkakao.blog.object.entity.Searchrank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchrankRepository extends JpaRepository<Searchrank, String> {
}
