package com.clova.hackathon.goodbyebunny.domain.review.api;

import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewCreateRequest;
import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewUpdateRequest;
import com.clova.hackathon.goodbyebunny.domain.review.api.response.ReviewReadResponse;
import com.clova.hackathon.goodbyebunny.domain.review.app.ReviewService;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public ResponseEntity<ReviewReadResponse> getReview(@AuthenticationPrincipal final MemberDetails member) {

        // member 닉네임 가져오기
        String memberNickname =member.getUsername();
        return reviewService.getReview(memberNickname);


    }

    @PatchMapping()
    public ResponseEntity<Long> updateReview(@AuthenticationPrincipal final MemberDetails member,
                                                             @RequestBody ReviewUpdateRequest request) {

        // member 닉네임 가져오기
        String memberNickname =member.getUsername();
        return reviewService.updateReview(memberNickname, request);


    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommend(@AuthenticationPrincipal final MemberDetails member){
        List<?> r=  reviewService.recommend(member.getNickname());
        return ResponseEntity.ok(r);
    }
}
