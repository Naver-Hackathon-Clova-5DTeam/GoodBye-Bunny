package com.clova.hackathon.goodbyebunny.domain.comment.api.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {

    private String commentContent;



}