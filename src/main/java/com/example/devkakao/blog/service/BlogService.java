package com.example.devkakao.blog.service;


import com.example.devkakao.blog.object.dto.KakaoBlogDTO;
import com.example.devkakao.blog.object.dto.NaverBlogDTO;
import com.example.devkakao.blog.object.dto.SearchKeywordDTO;
import com.example.devkakao.blog.object.entity.SearchKeyword;
import com.example.devkakao.blog.persistent.SearchKeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final SearchKeywordRepository searchrankRepository;
    private final ModelMapper modelMapper;

    public KakaoBlogDTO getSearchKakaoBlog(String target, int page, int size, String sort) {

        String api_key = "1475c041c4374a6cff8ff6c7a4edcc21";
        String respStr = "";

        //####################### Kakao API 호출 부분 START #############################
        try {
            Mono<String> mono = WebClient.builder()
                    .baseUrl("https://dapi.kakao.com")
                    .build().get()
                    .uri(buillder -> buillder.path("/v2/search/blog.json")
                            .queryParam("query", target)
                            .queryParam("sort", sort)
                            .queryParam("page", page)
                            .queryParam("size", size)
                            .build()
                    )
                    .header("Authorization", "KakaoAK " + api_key)
                    .exchangeToMono(response -> {
                        return response.bodyToMono(String.class);
                    });

            respStr = mono.block();

        } catch (Exception e) {
            System.out.println("Kakao API 호출 오류");
        }

        //####################### Kakao API 호출 부분 END ###############################


        //####################### 결과값 Parsing 부분 START #############################

        JSONObject respJson = new JSONObject();
        JSONObject respMeta = new JSONObject();
        JSONArray respDocuments = new JSONArray();

        try {
            respJson = (JSONObject) JSONValue.parse(respStr);         // 전체 결과 -> Json
            respMeta = (JSONObject) respJson.get("meta");             // Meta 결과 -> Json
            respDocuments = (JSONArray) respJson.get("documents");     // Documents 결과 -> Json
        } catch (Exception e) {
            System.out.println("JSON Parsing Error!");
        }

        KakaoBlogDTO kakaoBlogDTO = new KakaoBlogDTO();
        List<KakaoBlogDTO.Documents> documents = new ArrayList<>();
        KakaoBlogDTO.Meta meta = new KakaoBlogDTO.Meta();

        try {

            meta.setTotal_count((int) respMeta.get("total_count"));
            meta.setPageable_count((int) respMeta.get("pageable_count"));
            meta.setIs_end((Boolean) respMeta.get("is_end"));
            kakaoBlogDTO.setMeta(meta);

            System.out.println(kakaoBlogDTO.getMeta().getTotal_count());

            int number = 1;
            for (Object obj : respDocuments) {
                JSONObject respBlog = (JSONObject) obj;
                KakaoBlogDTO.Documents doc = new KakaoBlogDTO.Documents();
                doc.setPageNumber(page);
                doc.setBlogNumber(number++);
                doc.setBlogname((String) respBlog.get("blogname"));
                doc.setTitle((String) respBlog.get("title"));
                doc.setContents((String) respBlog.get("contents"));
                doc.setThumbnail((String) respBlog.get("thumbnail"));
                doc.setUrl((String) respBlog.get("url"));
                doc.setDateTime((String) respBlog.get("datetime"));
                //System.out.println(doc);
                documents.add(doc);
            }

            kakaoBlogDTO.setDocuments(documents);
            System.out.println(kakaoBlogDTO.getDocuments().get(0).getBlogname());
            //####################### 결과값 Parsing 부분 END #############################
        } catch (Exception e) {
            System.out.println("결과값 반환 객체 생성 오류");
        }
        return kakaoBlogDTO;
    }

    public NaverBlogDTO getSearchNaverBlog(String target, int page, int size, String sort) {

        String sortNaver = "sim";
        if(sort.equals("accuracy"))
            sort = "sim";
        else
            sort = "date";
        sortNaver = sort;

        String client_id = "tiqtmtEf2hsYVMnsMhjH";
        String client_key = "MhMmTRzAuN";
        String respStr = "";

        //####################### Naver API 호출 부분 START #############################
        try {
            String finalSortNaver = sortNaver;
            Mono<String> mono = WebClient.builder()
                    .baseUrl("https://openapi.naver.com")
                    .build().get()
                    .uri(buillder -> buillder.path("/v1/search/blog.json")
                            .queryParam("query", target)
                            .queryParam("sort", finalSortNaver)
                            .queryParam("start", size*(page-1)+1)
                            .queryParam("display", size)
                            .build()
                    )
                    .header("X-Naver-Client-Id", client_id)
                    .header("X-Naver-Client-Secret", client_key)
                    .exchangeToMono(response -> {
                        return response.bodyToMono(String.class);
                    });

            respStr = mono.block();

        } catch (Exception e) {
            System.out.println("Naver API 호출 오류");
        }

        //####################### Naver API 호출 부분 END ###############################


        //####################### 결과값 Parsing 부분 START #############################

        JSONObject respJson = new JSONObject();
        JSONArray respItems = new JSONArray();

        try {
            respJson = (JSONObject) JSONValue.parse(respStr);         // 전체 결과 -> Json
            respItems = (JSONArray) respJson.get("items");     // Documents 결과 -> Json
        } catch (Exception e) {
            System.out.println("JSON Parsing Error!");
        }

        NaverBlogDTO naverBlogDTO = new NaverBlogDTO();
        List<NaverBlogDTO.Items> items = new ArrayList<>();

        try {

            naverBlogDTO.setDisplay((int)respJson.get("display"));
            naverBlogDTO.setTotal((int)respJson.get("total"));
            naverBlogDTO.setStart((int)respJson.get("start"));
            naverBlogDTO.setLastBuildDate((String)respJson.get("lastBuildDate"));

            int number = 1;
            for (Object obj : respItems) {
                JSONObject respBlog = (JSONObject) obj;
                NaverBlogDTO.Items item = new NaverBlogDTO.Items();
                item.setPageNumber(page);
                item.setBlogNumber(number++);
                item.setTitle((String) respBlog.get("title"));
                item.setDescription((String)respBlog.get("description"));
                item.setLink((String)respBlog.get("link"));
                item.setBloggername((String)respBlog.get("bloggername"));
                item.setBloggerlink((String)respBlog.get("bloggerlink"));
                item.setPostdate((String)respBlog.get("postdate"));
                //System.out.println(doc);
                items.add(item);
            }

            naverBlogDTO.setItems(items);
            System.out.println(naverBlogDTO.getItems().get(0).getBloggerlink());
            //####################### 결과값 Parsing 부분 END #############################
        } catch (Exception e) {
            System.out.println("반환 객체 생성 에러!");
        }

        return naverBlogDTO;
    }

    public Optional<List<SearchKeyword>> getSearchTrend () {

        int LIMIT = 10;
        Page<SearchKeyword> page = searchrankRepository.findAll(PageRequest.of(0, LIMIT, Sort.by(Sort.Order.desc("count"))));
        return Optional.of(page.getContent());
    }

    public void stackSearchKeyword (String query) {

        Optional<SearchKeyword> optionalSearchKeyword = searchrankRepository.findByTarget(query);
        if(optionalSearchKeyword.isPresent()) {
            SearchKeywordDTO searchKeywordDTO = modelMapper.map(optionalSearchKeyword.get(), SearchKeywordDTO.class);
            searchKeywordDTO.setCount(searchKeywordDTO.getCount()+1);
            SearchKeyword searchKeyword = optionalSearchKeyword.get();
            searchKeyword.update(searchKeywordDTO);
            try {
                searchrankRepository.save(searchKeyword);
            } catch (Exception e) {
                System.out.println("JPA Save 오류");
            }
        }
        else {
            SearchKeywordDTO newKeyword = new SearchKeywordDTO();
            newKeyword.setTarget(query);
            newKeyword.setCount(1);
            SearchKeyword searchKeyword = modelMapper.map(newKeyword, SearchKeyword.class);
            try {
                searchrankRepository.save(searchKeyword);
            } catch (Exception e) {
                System.out.println("JPA Save 오류");
            }
        }
    }

}
