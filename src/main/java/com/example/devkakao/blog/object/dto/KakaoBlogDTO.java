package com.example.devkakao.blog.object.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ToString
@NoArgsConstructor
@Getter
@Setter
public class KakaoBlogDTO {

    String source = "KakaoBlog";
    Meta meta;
    List<Documents> documents;

    @Getter
    @Setter
    public static class Meta{

        @Schema(description = "검색된 문서 수", example = "5")
        int total_count;

        @Schema(description = "total_count 중 노출 가능 문서 수", example = "5")
        int pageable_count;

        @Schema(description = "현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음", example = "true")
        Boolean is_end;

    }

    @Getter
    @Setter
    public static class Documents{

        @Schema(description = "페이지 번호", example = "1")
        int pageNumber;

        @Schema(description = "페이지 내 글 번호", example = "1")
        int blogNumber;

        @Schema(description = "블로그 글 제목", example = "작은 <b>집</b> <b>짓기</b> 기본컨셉 - <b>집</b><b>짓기</b> 초기구상하기")
        String title;

        @Schema(description = "블로그 글 요약", example = "이 점은 <b>집</b>을 지으면서 고민해보아야 한다. 하지만, 금액에 대한 가성비 대비 크게 문제되지 않을 부분이라 생각하여 설계로 극복하자고 생각했다. 전체 <b>집</b><b>짓기</b>의 기본방향은 크게 세 가지이다. 우선은 여가의 영역 증대이다. 현대 시대 일도 중요하지만, 여가시간 <b>집</b>에서 어떻게 보내느냐가 중요하니깐 이를 기본적...")
        String contents;

        @Schema(description = "블로그 글 URL", example = "https://brunch.co.kr/@tourism/91")
        String url;

        @Schema(description = "블로그의 이름", example = "정란수의 브런치")
        String blogname;

        @Schema(description = "검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음", example = "http://search3.kakaocdn.net/argon/130x130_85_c/7r6ygzbvBDc")
        String thumbnail;

        @Schema(description = "블로그 글 작성시간", example = "2017-05-07T18:50:07.000+09:00")
        String dateTime;

    }
}

