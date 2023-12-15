package com.clova.hackathon.goodbyebunny.domain.member.model.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberReviewDto {
    private String memberNickname;
    private String title;
    private Long reviewId;
    private LocalDateTime updateTime;
    private String content;
    private List<String> keywords;
    private String profile;

    public MemberReviewDto(String memberNickname, String title, Long reviewId, LocalDateTime updateTime, String content, List<String> keywords, String profile) {
        this.memberNickname = memberNickname;
        this.title = title;
        this.reviewId = reviewId;
        this.updateTime = updateTime;
        this.content = content;
        this.keywords = keywords;
        this.profile = profile;
    }

}