package com.clova.hackathon.goodbyebunny.domain.item.api.request;


import com.clova.hackathon.goodbyebunny.domain.item.model.ItemType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickItemRequest {
    private ItemType type;
    private int itemNum;
}
