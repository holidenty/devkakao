package com.example.devkakao.blog.service;


import com.example.devkakao.blog.object.dto.KakaoBlogDTO;
import com.example.devkakao.blog.persistent.SearchrankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final SearchrankRepository searchrankRepository;
    private final ModelMapper modelMapper;


    public KakaoBlogDTO getSearchKakaoBlog(String target) {

        String api_key = "1475c041c4374a6cff8ff6c7a4edcc21";

        int page;
        int size;

        Mono<String> mono = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .build().get()
                .uri(buillder -> buillder.path("/v2/search/blog.json")
                        .queryParam("query", target)
                        .build()
                )
                .header("Authorization", "KakaoAK " + api_key)
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });

        String respStr = mono.block();
        JSONObject respJson = (JSONObject) JSONValue.parse(respStr);

        JSONObject respMeta = (JSONObject) respJson.get("meta");
        JSONArray respDocuments = (JSONArray) respJson.get("documents");

        KakaoBlogDTO kakaoBlogDTO = new KakaoBlogDTO();
        List<KakaoBlogDTO.Documents> documents = new ArrayList<>();
        KakaoBlogDTO.Meta meta = new KakaoBlogDTO.Meta();

        meta.setTotal_count((int)respMeta.get("total_count"));
        meta.setPageable_count((int)respMeta.get("pageable_count"));
        meta.setIs_end((Boolean)respMeta.get("is_end"));
        kakaoBlogDTO.setMeta(meta);

        System.out.println(kakaoBlogDTO.getMeta().getTotal_count());

        for(Object obj : respDocuments) {
            JSONObject respBlog = (JSONObject) obj;
            KakaoBlogDTO.Documents doc = new KakaoBlogDTO.Documents();
            doc.setBlogname((String)respBlog.get("blogname"));
            doc.setTitle((String)respBlog.get("title"));
            doc.setContents((String)respBlog.get("contents"));
            doc.setThumbnail((String)respBlog.get("thumbnail"));
            doc.setUrl((String)respBlog.get("url"));
            doc.setDateTime((String)respBlog.get("datetime"));
            //System.out.println(doc);
            documents.add(doc);
        }

        kakaoBlogDTO.setDocuments(documents);

        System.out.println(kakaoBlogDTO.getDocuments().get(0).getBlogname());

        return kakaoBlogDTO;
    }

    /*
    public List<NaverBlogDTO> getSearchNaverBlog(String target){
        return ;
    }
     */

}
