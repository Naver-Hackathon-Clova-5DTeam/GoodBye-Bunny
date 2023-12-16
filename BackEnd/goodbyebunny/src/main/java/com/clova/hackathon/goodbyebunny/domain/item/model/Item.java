package com.clova.hackathon.goodbyebunny.domain.item.model;


import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    private int price;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<MemberItem> memberItems;

    private int itemNum;

    @Enumerated(value = EnumType.STRING)
    private ItemType type;



}
