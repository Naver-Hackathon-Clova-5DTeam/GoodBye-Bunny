package com.clova.hackathon.goodbyebunny.domain.item.api;


import com.clova.hackathon.goodbyebunny.domain.item.app.ItemService;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    @GetMapping("/")
    public ResponseEntity<?> getItem(@AuthenticationPrincipal final MemberDetails member){
        List<?> result= itemService.getItemList(member.getNickname());
        return ResponseEntity.ok(result);
    }
}
