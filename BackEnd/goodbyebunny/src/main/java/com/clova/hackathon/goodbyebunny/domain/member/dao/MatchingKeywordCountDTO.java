package com.clova.hackathon.goodbyebunny.domain.member.dao;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MatchingKeywordCountDTO {

    private Long memberId;
    private int age;
    private List<String> keywords;
    private int matchingKeywordCount;


}