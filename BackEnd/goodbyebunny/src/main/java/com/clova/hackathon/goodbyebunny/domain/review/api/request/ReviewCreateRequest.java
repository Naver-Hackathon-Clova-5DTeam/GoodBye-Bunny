package com.clova.hackathon.goodbyebunny.domain.review.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    private String title;
    private String content;

}
