package com.clova.hackathon.goodbyebunny.domain.item.dao;

import com.clova.hackathon.goodbyebunny.domain.item.model.ItemDTO;
import com.clova.hackathon.goodbyebunny.domain.member.dao.CustomMemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.member.model.dto.MemberReviewDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.clova.hackathon.goodbyebunny.domain.item.model.QItem.item;
import static com.clova.hackathon.goodbyebunny.domain.item.model.QMemberItem.memberItem;
import static com.querydsl.core.types.Projections.bean;

@Repository
@RequiredArgsConstructor
public class CustomItemRepositoryImpl implements CustomItemRepository {
    private final JPAQueryFactory queryFactory;
    public List<?> getItemList(Long memberId) {
        return queryFactory
                .select(
                        bean(ItemDTO.class,
                                item.itemNum.as("itemNum"),
                                memberItem.member.isNotNull().as("purchased"),
                                item.price.as("price"),
                                item.type.as("type")
                        )
                )
                .from(item)
                .leftJoin(memberItem).on(item.id.eq(memberItem.item.id).and(memberItem.member.id.eq(memberId)))
                .fetch();

    }


}
