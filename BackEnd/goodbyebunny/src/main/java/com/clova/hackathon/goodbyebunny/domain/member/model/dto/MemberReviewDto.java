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
    private String nickname;
    private String title;
    private Long id;
    private LocalDateTime updatedDate;
    private String content;
    private List<String> words;

    public MemberReviewDto(String nickname, String title, Long id, LocalDateTime updatedDate, String content, List<String> words) {
        this.nickname = nickname;
        this.title = title;
        this.id = id;
        this.updatedDate = updatedDate;
        this.content = content;
        this.words = words;
    }

}