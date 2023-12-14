package com.clova.hackathon.goodbyebunny.domain.review.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewReadResponse {

    private String memberNickname;
    private String title;
    private String content;
    private LocalDateTime updateTime;

    public static ReviewReadResponse of(String memberNickname, String title, String content, LocalDateTime reviewUpdateTime) {
        return ReviewReadResponse.builder()
                .memberNickname(memberNickname)
                .title(title)
                .content(content)
                .updateTime(reviewUpdateTime)
                .build();
    }

}
