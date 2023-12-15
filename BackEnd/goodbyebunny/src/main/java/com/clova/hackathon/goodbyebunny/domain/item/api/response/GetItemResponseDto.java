package com.clova.hackathon.goodbyebunny.domain.item.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetItemResponseDto {
    private int background_id;
    private int dress_id;

}
