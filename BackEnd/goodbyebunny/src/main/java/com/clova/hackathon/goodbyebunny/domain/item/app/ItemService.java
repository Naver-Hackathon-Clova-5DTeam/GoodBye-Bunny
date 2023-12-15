package com.clova.hackathon.goodbyebunny.domain.item.app;


import com.clova.hackathon.goodbyebunny.domain.item.dao.CustomItemRepositoryImpl;
import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final MemberRepository memberRepository;
    private final CustomItemRepositoryImpl customItemRepository;

    public List<?> getItemList(String nickname){
        Member member =memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));

        return customItemRepository.getItemList(member.getId());
    }
}
