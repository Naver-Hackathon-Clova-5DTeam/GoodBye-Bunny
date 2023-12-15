package com.clova.hackathon.goodbyebunny.domain.member.dao;

import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.member.model.dto.MemberReviewDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.clova.hackathon.goodbyebunny.domain.keyword.model.QKeyword.keyword;
import static com.clova.hackathon.goodbyebunny.domain.member.model.QMember.member;
import static com.clova.hackathon.goodbyebunny.domain.review.model.QReview.review;
import static com.clova.hackathon.goodbyebunny.domain.review.model.QReviewKeyword.reviewKeyword;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository{

    private final JPAQueryFactory queryFactory;
    private final MemberRepository memberRepository;

    public List<?> getMatchingMembers(int age, Long targetId) {
        List<String> targetKeywords = queryFactory
                .select(keyword.word)
                .from(member)
                .leftJoin(review).on(member.id.eq(review.member.id))
                .leftJoin(reviewKeyword).on(review.id.eq(reviewKeyword.review.id))
                .leftJoin(keyword).on(keyword.id.eq(reviewKeyword.keyword.id))
                .where(member.id.eq(targetId))
                .distinct()
                .fetch();

        List<Long> memberIds=  queryFactory
                .select(member.id)
                .from(member)
                .leftJoin(review).on(member.id.eq(review.member.id))
                .leftJoin(reviewKeyword).on(review.id.eq(reviewKeyword.review.id))
                .leftJoin(keyword).on(keyword.id.eq(reviewKeyword.keyword.id))
                .where(member.age.between(age-5,age+5).and(keyword.word.in(targetKeywords))).where(member.id.ne(targetId))
                .distinct()
                .fetch();

        List<MemberReviewDto> result = new ArrayList<>();

        for (Long memberId : memberIds) {
            Member member1 = memberRepository.findById(memberId).get();
            List<String> return_keyword= queryFactory
                    .select(keyword.word)
                    .from(member)
                    .leftJoin(review).on(member.id.eq(review.member.id))
                    .leftJoin(reviewKeyword).on(review.id.eq(reviewKeyword.review.id))
                    .leftJoin(keyword).on(keyword.id.eq(reviewKeyword.keyword.id))
                    .where(member.id.eq(member1.getId()))
                    .fetch();

            MemberReviewDto dto = new MemberReviewDto(
                    member1.getNickname(),
                    member1.getReview().getTitle(),
                    member1.getReview().getId(),
                    member1.getReview().getUpdatedDate(),
                    member1.getReview().getContent(),
                    return_keyword
            );
            result.add(dto);
        }
        return result;
    }

}
