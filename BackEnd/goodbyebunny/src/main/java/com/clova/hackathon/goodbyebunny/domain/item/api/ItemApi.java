package com.clova.hackathon.goodbyebunny.domain.item.api;


import com.clova.hackathon.goodbyebunny.domain.item.api.request.PickItemRequest;
import com.clova.hackathon.goodbyebunny.domain.item.api.request.PurchaseItemRequest;
import com.clova.hackathon.goodbyebunny.domain.item.api.response.GetItemResponseDto;
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

    @GetMapping("/list")
    public ResponseEntity<?> getItemList(@AuthenticationPrincipal final MemberDetails member){
        List<?> result= itemService.getItemList(member.getNickname());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody final PurchaseItemRequest request, @AuthenticationPrincipal final MemberDetails member){
        return itemService.purchaseItem(request,member.getNickname());

    }

    @PostMapping("/pick")
    public ResponseEntity<?> pick(@RequestBody final PickItemRequest request, @AuthenticationPrincipal final MemberDetails member){
        itemService.pickItem(request,member.getNickname());

        return ResponseEntity.ok(null);

    }

    @GetMapping("")
    public ResponseEntity<?> getItem(@AuthenticationPrincipal final MemberDetails member){
        GetItemResponseDto item = itemService.getItem(member.getNickname());

        return ResponseEntity.ok(item);

    }
}
