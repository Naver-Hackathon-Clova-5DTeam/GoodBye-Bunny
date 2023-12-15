package com.clova.hackathon.goodbyebunny.global.config;

import com.clova.hackathon.goodbyebunny.domain.keyword.dao.KeywordRepository;
import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final KeywordRepository keywordRepository;

    //@TODO: 파일에서 불러오기
    private final String[] initialKeywords = {"가족", "친구", "연인", "학업", "업무", "직장", "건강", "여행", "취미"};


    @EventListener(ApplicationReadyEvent.class)
    public void insertDataOnStart() {
        for (String keyword : initialKeywords) {
            // 키워드가 없으면 키워드 테이블에 넣기
            insertIfNotExist(keyword);
        }

    }

    private void insertIfNotExist(String word) {
        if (keywordRepository.findKeywordByWord(word).isEmpty()) {
            // 키워드가 데이터베이스에 없으면
            Keyword keyword = Keyword.builder()
                    .word(word)
                    .build();
            keywordRepository.save(keyword);
        }

    }
}
