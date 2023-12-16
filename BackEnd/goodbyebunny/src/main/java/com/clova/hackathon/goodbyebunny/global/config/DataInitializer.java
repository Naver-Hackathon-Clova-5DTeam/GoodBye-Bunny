package com.clova.hackathon.goodbyebunny.global.config;

import com.clova.hackathon.goodbyebunny.domain.item.dao.ItemRepository;
import com.clova.hackathon.goodbyebunny.domain.item.model.Item;
import com.clova.hackathon.goodbyebunny.domain.item.model.ItemType;
import com.clova.hackathon.goodbyebunny.domain.keyword.dao.KeywordRepository;
import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final KeywordRepository keywordRepository;
    private final ItemRepository itemRepository;


    //@TODO: 파일에서 불러오기
    private final String[] initialKeywords = {"가족", "친구", "연인", "학업", "업무", "직장", "건강", "여행", "취미"};
    private final int[] initialItems = {15, 22, 31, 19, 46, 34, 8, 15, 15 ,15};


    @EventListener(ApplicationReadyEvent.class)
    public void insertDataOnStart() {
        // keyword 넣기
        for (String keyword : initialKeywords) {
            // 키워드가 없으면 키워드 테이블에 넣기
            insertKeywordsIfNotExist(keyword);
        }

        // item 넣기
        for (int idx = 0; idx < initialItems.length; idx++) {
            // 키워드가 없으면 키워드 테이블에 넣기
            insertItemsIfNotExist(idx+1);
        }

    }

    private void insertKeywordsIfNotExist(String word) {
        if (keywordRepository.findKeywordByWord(word).isEmpty()) {
            // 키워드가 데이터베이스에 없으면
            Keyword keyword = Keyword.builder()
                    .word(word)
                    .build();
            keywordRepository.save(keyword);
        }

    }

    private void insertItemsIfNotExist(int itemNum) {
        if (itemRepository.findItemByItemNum(itemNum).isEmpty()) {
            // 키워드가 데이터베이스에 없으면
            Set<Integer> dressNums = Set.of(8, 9, 10);
            if (dressNums.contains(itemNum) ){
                Item item = Item.builder()
                        .itemNum(itemNum)
                        .price(initialItems[itemNum-1])
                        .type(ItemType.BACKGROUND)
                        .build();
                itemRepository.save(item);

            }
            else {
                Item item = Item.builder()
                        .itemNum(itemNum)
                        .price(initialItems[itemNum-1])
                        .type(ItemType.DRESS)
                        .build();
                itemRepository.save(item);

            }
        }

    }
}
