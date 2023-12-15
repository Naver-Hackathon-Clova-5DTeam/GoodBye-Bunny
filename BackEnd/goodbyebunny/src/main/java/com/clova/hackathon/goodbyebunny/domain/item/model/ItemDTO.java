package com.clova.hackathon.goodbyebunny.domain.item.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private int itemNum;
    private boolean purchased;
    private int price;
    private ItemType type;
}
