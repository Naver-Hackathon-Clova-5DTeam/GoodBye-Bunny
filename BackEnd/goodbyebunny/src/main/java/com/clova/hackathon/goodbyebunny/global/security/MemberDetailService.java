package com.clova.hackathon.goodbyebunny.global.security;

import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailService {

    private final MemberRepository memberRepository;

    public MemberDetails loadMemberByEmail(String nickname){

        Member member = memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        return new MemberDetails(nickname, member.getPassword());
    }
}
