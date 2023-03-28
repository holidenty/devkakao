package com.example.devkakao.blog.service;


import com.example.devkakao.blog.object.dto.KakaoBlogDTO;
import com.example.devkakao.blog.persistent.SearchrankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final SearchrankRepository searchrankRepository;
    private final ModelMapper modelMapper;


    public String getSearchKakaoBlog(String target) throws Exception{

        String result ="";
        String api_key = "KakaoAK " + "1475c041c4374a6cff8ff6c7a4edcc21";
        String query = "query=" + target;
        String api_url = "https://dapi.kakao.com/v2/search/blog?" + query;
        String sort = "accuracy";

        System.out.println(api_key);
        int page;
        int size;

        try {
            JSONObject req = new JSONObject();

            URL url = new URL(api_url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Authorization", api_key);
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(req.toString());
            os.flush();

            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            JSONObject jsonObj = (JSONObject) JSONValue.parse(input.readLine());

            System.out.println(jsonObj);

            input.close();
            conn.disconnect();



            KakaoBlogDTO kakaoBlogDTO = new KakaoBlogDTO();
            System.out.println(kakaoBlogDTO.getMetaInfo());

            result = "response, meta :: " + jsonObj.get("meta");
            JSONObject jsonResult = (JSONObject) JSONValue.parse(result);
            System.out.println();
            result = "response, documents :: " + jsonObj.get("documents");
            System.out.println(jsonResult.get("total_count"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
    public List<NaverBlogDTO> getSearchNaverBlog(String target){
        return ;
    }
     */

}
