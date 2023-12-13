package com.clova.hackathon.goodbyebunny.domain.review.app;

import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewCreateRequest;
import com.clova.hackathon.goodbyebunny.domain.review.api.response.ReviewReadResponse;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.domain.review.dao.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {


    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    // Review 작성
    public ResponseEntity<Long> createReview(String memberNickname, ReviewCreateRequest request) {

        Optional<Member> member = memberRepository.findMemberByNickname(memberNickname);

        Review review = Review.builder()
                .member(member.get())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Long savedCommentId = reviewRepository.save(review).getId();

        return ResponseEntity.ok(savedCommentId);

    }

    // 나의 Review 조회
    public ResponseEntity<ReviewReadResponse> getReview(String memberNickname) {


        //member 조회
        Member member = memberRepository.findMemberByNickname(memberNickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        //review 조회
        Review review = reviewRepository.findReviewByMemberId(member.getId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회고입니다."));


        return ResponseEntity.ok(ReviewReadResponse.of(review.getTitle(), review.getContent(),review.getUpdatedDate()));

    }
}
