package com.clova.hackathon.goodbyebunny.domain.member.app;


import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import com.clova.hackathon.goodbyebunny.domain.member.api.request.LoginMemberRequest;
import com.clova.hackathon.goodbyebunny.domain.member.api.request.SignUpMemberRequest;
import com.clova.hackathon.goodbyebunny.domain.member.dao.CustomMemberRepositoryImpl;
import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CustomMemberRepositoryImpl customMemberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void join(SignUpMemberRequest request){
        memberRepository.save(Member.builder()
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .build());
    }

    public String login(LoginMemberRequest request){
        Member member = memberRepository.findMemberByNickname(request.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));

        if (!passwordEncoder.matches(request.getPassword(),member.getPassword())){
            throw new IllegalArgumentException("로그인 실패.");
        }

         return jwtTokenProvider.createAccessToken(member.getNickname());
    }

    public List<Member> search(String nickName){
        Member member = memberRepository.findMemberByNickname(nickName)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));
        return customMemberRepository.getMatchingMembers();
    }
}
