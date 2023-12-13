package com.clova.hackathon.goodbyebunny.domain.comment.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class CommentReadResponse {

    private Long commentId;
    private String commentContent;
    private LocalDateTime updateTime;


    public static CommentReadResponse of(Long commentId, String content, LocalDateTime reviewUpdateTime) {
        return CommentReadResponse.builder()
                .commentId(commentId)
                .commentContent(content)
                .updateTime(reviewUpdateTime)
                .build();

    }

}

