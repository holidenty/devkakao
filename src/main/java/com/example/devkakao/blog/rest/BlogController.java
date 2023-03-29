package com.example.devkakao.blog.rest;

import com.example.devkakao.blog.service.BlogService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/blog")
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @Operation(summary= "카카오 블로그 검색", description = "카카오 블로그 글 검색 결과 조회 API", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/search/{target}")
    public ResponseEntity<Object> getSearchBlog(@PathVariable String target,
                                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                @RequestParam(name = "sort", required = false, defaultValue = "accuracy") String sort) throws Exception {
        System.out.println(target);
        System.out.println(page);
        System.out.println(size);
        System.out.println(sort);

        blogService.stackSearchKeyword(target);
        return new ResponseEntity<>(blogService.getSearchKakaoBlog(target, page, size, sort), HttpStatus.OK);
    }

    @Operation(summary= "네이버 블로그 검색", description = "네이버 블로그 글 검색 결과 조회 API", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/search/naver/{target}")
    public ResponseEntity<Object> getSearchNaverBlog(@PathVariable String target,
                                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                @RequestParam(name = "sort", required = false, defaultValue = "accuracy") String sort) throws Exception {
        System.out.println(target);
        System.out.println(page);
        System.out.println(size);
        System.out.println(sort);
        blogService.stackSearchKeyword(target);
        return new ResponseEntity<>(blogService.getSearchNaverBlog(target, page, size, sort), HttpStatus.OK);
    }

    @GetMapping(value = "/search/trend")
    public ResponseEntity<Object> getSearchTrendRank() throws Exception {
        return new ResponseEntity<>(blogService.getSearchTrend(), HttpStatus.OK);
    }
}
