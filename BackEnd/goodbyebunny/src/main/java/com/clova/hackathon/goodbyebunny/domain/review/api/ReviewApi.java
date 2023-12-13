package com.clova.hackathon.goodbyebunny.domain.review.api;

import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewContentRequest;
import com.clova.hackathon.goodbyebunny.domain.review.app.ReviewService;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewApi {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<Long> postReview(@AuthenticationPrincipal final MemberDetails member,
                                     @RequestBody ReviewContentRequest request) {

        // member 닉네임 가져오기
        String memberNickname =member.getUsername();
        return reviewService.createReview(memberNickname, request);


    }

}
