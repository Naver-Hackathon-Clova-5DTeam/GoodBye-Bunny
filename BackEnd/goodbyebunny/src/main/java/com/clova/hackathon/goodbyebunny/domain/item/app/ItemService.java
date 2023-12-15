package com.clova.hackathon.goodbyebunny.domain.item.app;


import com.clova.hackathon.goodbyebunny.domain.item.dao.CustomItemRepositoryImpl;
import com.clova.hackathon.goodbyebunny.domain.item.dao.ItemRepository;
import com.clova.hackathon.goodbyebunny.domain.item.dao.MemberItemRepository;
import com.clova.hackathon.goodbyebunny.domain.item.model.Item;
import com.clova.hackathon.goodbyebunny.domain.item.model.MemberItem;
import com.clova.hackathon.goodbyebunny.domain.item.request.PurchaseItemRequest;
import com.clova.hackathon.goodbyebunny.domain.member.api.request.LoginMemberRequest;
import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final MemberRepository memberRepository;
    private final CustomItemRepositoryImpl customItemRepository;
    private final ItemRepository itemRepository;
    private final MemberItemRepository memberItemRepository;

    public List<?> getItemList(String nickname){
        Member member =memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));

        return customItemRepository.getItemList(member.getId());
    }

    @Transactional
    public ResponseEntity<?> purchaseItem(PurchaseItemRequest request, String nickname) {
        Member member = memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));
        Item item = itemRepository.findItemByItemNumAndType(request.getItemNum(), request.getType())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다."));
        if (member.getPoint() < item.getPrice()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("포인트가 부족합니다");
        }
        member.updateMember(member.getPoint()-item.getPrice());
        memberRepository.save(member);

        memberItemRepository.save(MemberItem.builder()
                .member(member)
                .item(item)
                .build());
        return ResponseEntity.ok(null);
    }
}
