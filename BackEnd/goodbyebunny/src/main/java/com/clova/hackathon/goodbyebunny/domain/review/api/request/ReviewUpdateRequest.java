package com.clova.hackathon.goodbyebunny.domain.review.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewUpdateRequest {

    private String title;
    private String content;

}
