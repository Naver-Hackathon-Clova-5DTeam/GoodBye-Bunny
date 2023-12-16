package com.clova.hackathon.goodbyebunny.domain.item.dao;

import com.clova.hackathon.goodbyebunny.domain.item.model.Item;
import com.clova.hackathon.goodbyebunny.domain.item.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findItemByItemNumAndType(int itemNum, ItemType type);
    Optional<Item> findItemByItemNum(int itemNum);



}
