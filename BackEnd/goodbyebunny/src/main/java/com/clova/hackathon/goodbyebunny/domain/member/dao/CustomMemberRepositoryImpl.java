package com.clova.hackathon.goodbyebunny.domain.member.dao;

import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import com.clova.hackathon.goodbyebunny.domain.keyword.model.QKeyword;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.model.QReview;
import com.clova.hackathon.goodbyebunny.domain.review.model.QReviewKeyword;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.clova.hackathon.goodbyebunny.domain.member.model.QMember;

import java.util.List;

import static com.clova.hackathon.goodbyebunny.domain.member.model.QMember.member;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository{

    private final JPAQueryFactory queryFactory;

    public List<Member> getMatchingMembers() {

        QMember m = QMember.member;
        QReview r = QReview.review;
        QReviewKeyword rk = QReviewKeyword.reviewKeyword;
        QKeyword k = QKeyword.keyword;

        List<Member> rss = queryFactory
                .select(m)
                .from(m)
                .fetch();

        System.out.println("test");
        System.out.println(rss.toString());

        return rss;

    }

}
