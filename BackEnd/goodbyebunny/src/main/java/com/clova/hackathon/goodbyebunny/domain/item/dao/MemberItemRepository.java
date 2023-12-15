package com.clova.hackathon.goodbyebunny.domain.item.dao;

import com.clova.hackathon.goodbyebunny.domain.item.model.MemberItem;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {
}
