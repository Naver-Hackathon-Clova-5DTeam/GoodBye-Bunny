package com.clova.hackathon.goodbyebunny.domain.review.api;

import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewCreateRequest;
import com.clova.hackathon.goodbyebunny.domain.review.api.response.ReviewReadResponse;
import com.clova.hackathon.goodbyebunny.domain.review.app.ReviewService;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewApi {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<Long> postReview(@AuthenticationPrincipal final MemberDetails member,
                                     @RequestBody ReviewCreateRequest request) {

        // member 닉네임 가져오기
        String memberNickname =member.getUsername();
        return reviewService.createReview(memberNickname, request);


    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewReadResponse> getReview(@AuthenticationPrincipal final MemberDetails member,
                                                        @PathVariable("review_id") Long reviewId) {

        // member 닉네임 가져오기
        String memberNickname =member.getUsername();
        return reviewService.getReview(memberNickname, reviewId);


    }

}
