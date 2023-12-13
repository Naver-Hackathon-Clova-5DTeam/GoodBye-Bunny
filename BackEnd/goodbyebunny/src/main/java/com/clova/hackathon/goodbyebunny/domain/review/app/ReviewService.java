package com.clova.hackathon.goodbyebunny.domain.review.app;

import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.api.request.ReviewContentRequest;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.domain.review.repository.ReviewRepository;
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
    public ResponseEntity<Long> createReview(String memberNickname, ReviewContentRequest request) {

        Optional<Member> member = memberRepository.findMemberByNickname(memberNickname);

        Review review = Review.builder()
                .member(member.get())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Long savedCommentId = reviewRepository.save(review).getId();

        return ResponseEntity.ok(savedCommentId);

    }
}
