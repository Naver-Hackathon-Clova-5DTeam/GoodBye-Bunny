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

        Member member = memberRepository.findMemberByNickname(memberNickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        reviewRepository.findReviewByMemberId(member.getId())
                .ifPresent((_review) -> {
                    throw new IllegalStateException("작성한 회고가 존재합니다.");
                });

        Review review = Review.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Long savedCommentId = reviewRepository.save(review).getId();

        return ResponseEntity.ok(savedCommentId);

    }

    // Review 조회
    public ResponseEntity<ReviewReadResponse> getReview(String memberNickname, Long reviewId) {

        //review 조회
        Review review = reviewRepository.findReviewById(reviewId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회고입니다."));
        //member 조회
        Member member = memberRepository.findMemberByNickname(memberNickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        //review과 memberId의 유효성 체크
        if (review.getMember().getId() != member.getId()){
            throw new IllegalStateException("허가되지 않은 접근입니다.");
        }

        return ResponseEntity.ok(ReviewReadResponse.of(review.getTitle(), review.getContent(),review.getUpdatedDate()));

    }
}
