package com.clova.hackathon.goodbyebunny.domain.item.dao;

import com.clova.hackathon.goodbyebunny.domain.item.model.PickedItem;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PickedItemRepository extends JpaRepository<PickedItem, Long> {
    Optional<PickedItem> findPickedItemByMember(Member member);
}
