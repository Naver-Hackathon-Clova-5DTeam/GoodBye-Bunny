package com.clova.hackathon.goodbyebunny.domain.item.request;

import com.clova.hackathon.goodbyebunny.domain.item.model.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseItemRequest {
    private ItemType type;
    private int itemNum;
}
