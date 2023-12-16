package com.clova.hackathon.goodbyebunny.domain.comment.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentListResponse {

    private Long id;
    private String content;
    private LocalDateTime updated_date;
    private String nickname;
}
