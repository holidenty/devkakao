package com.example.devkakao.blog.rest;

import com.example.devkakao.blog.service.BlogService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<Object> getSearchBlog(@Parameter(name="target", description = "검색 키워드") @PathVariable(name = "target") String target,
                                                @Parameter(name="page", description = "Pagination, 불러오고 싶은 페이지 번호") @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                @Parameter(name="size", description = "Pagination, 페이지당 자료 갯수") @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                @Parameter(name="sort", description = "정렬 방식 (accuracy : 정확도, recency : 최신)") @RequestParam(name = "sort", required = false, defaultValue = "accuracy") String sort)
            throws Exception {

        Object object;
        String error = "현재 검색 API가 정상적으로 동작하지 않습니다.";

        try {
            blogService.stackSearchKeyword(target);
        } catch (Exception e) {
            System.out.println("인기검색어 데이터 저장 오류");
        }
        try {
            object = blogService.getSearchKakaoBlog(target, page, size, sort);
            return new ResponseEntity<>(object, HttpStatus.OK);
        } catch (Exception e) {

            try {
                System.out.println("Kakao Blog 데이터 검색 실패, Naver Blog 데이터 검색으로 전환");
                object = blogService.getSearchNaverBlog(target, page, size, sort);
                return new ResponseEntity<>(object, HttpStatus.OK);
            } catch (Exception exception) {
                System.out.println("Naver Blog 데이터 검색 실패");
                return new ResponseEntity<>(error, HttpStatus.OK);
            }
        }
    }

    @Operation(summary= "네이버 블로그 검색", description = "네이버 블로그 글 검색 결과 조회 API", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/search/naver/{target}")
    public ResponseEntity<Object> getSearchNaverBlog(@Parameter(name="target", description = "검색 키워드") @PathVariable(name = "target") String target,
                                                     @Parameter(name="page", description = "Pagination, 불러오고 싶은 페이지 번호") @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                     @Parameter(name="size", description = "Pagination, 페이지당 자료 갯수") @RequestParam(name = "size", required = false, defaultValue = "10") int size,
                                                     @Parameter(name="sort", description = "정렬 방식 (accuracy : 정확도, recency : 최신)") @RequestParam(name = "sort", required = false, defaultValue = "accuracy") String sort)
            throws Exception {

        blogService.stackSearchKeyword(target);
        return new ResponseEntity<>(blogService.getSearchNaverBlog(target, page, size, sort), HttpStatus.OK);
    }

    @Operation(summary= "인기검색어 조회 API", description = "인기 검색어 상위 10개 조회 API", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/search/trend")
    public ResponseEntity<Object> getSearchTrendRank() throws Exception {
        return new ResponseEntity<>(blogService.getSearchTrend(), HttpStatus.OK);
    }
}
