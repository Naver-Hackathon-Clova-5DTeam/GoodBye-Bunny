package com.clova.hackathon.goodbyebunny.domain.member.api;

import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import com.clova.hackathon.goodbyebunny.domain.member.api.request.LoginMemberRequest;
import com.clova.hackathon.goodbyebunny.domain.member.api.request.SignUpMemberRequest;
import com.clova.hackathon.goodbyebunny.domain.member.app.MemberService;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody final SignUpMemberRequest request){
        memberService.join(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginMemberRequest request){
        String token =  memberService.login(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/test")
    public String test(@AuthenticationPrincipal final MemberDetails member){
        return member.getNickname();
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@AuthenticationPrincipal final MemberDetails member){
        List<?> r=  memberService.search(member.getNickname());
        return ResponseEntity.ok(r);
    }
}
