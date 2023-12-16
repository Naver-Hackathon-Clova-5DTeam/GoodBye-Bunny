package com.clova.hackathon.goodbyebunny.domain.item.dao;

import com.clova.hackathon.goodbyebunny.domain.item.model.Item;
import com.clova.hackathon.goodbyebunny.domain.item.model.ItemType;
import com.clova.hackathon.goodbyebunny.domain.keyword.model.Keyword;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByItemNumAndType(int itemNum, ItemType type);
    Optional<Item> findItemByItemNum(int itemNum);



}
