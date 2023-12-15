package com.clova.hackathon.goodbyebunny.domain.item.app;


import com.clova.hackathon.goodbyebunny.domain.item.api.request.PickItemRequest;
import com.clova.hackathon.goodbyebunny.domain.item.api.response.GetItemResponseDto;
import com.clova.hackathon.goodbyebunny.domain.item.dao.CustomItemRepositoryImpl;
import com.clova.hackathon.goodbyebunny.domain.item.dao.ItemRepository;
import com.clova.hackathon.goodbyebunny.domain.item.dao.MemberItemRepository;
import com.clova.hackathon.goodbyebunny.domain.item.dao.PickedItemRepository;
import com.clova.hackathon.goodbyebunny.domain.item.model.Item;
import com.clova.hackathon.goodbyebunny.domain.item.model.ItemType;
import com.clova.hackathon.goodbyebunny.domain.item.model.MemberItem;
import com.clova.hackathon.goodbyebunny.domain.item.api.request.PurchaseItemRequest;
import com.clova.hackathon.goodbyebunny.domain.item.model.PickedItem;
import com.clova.hackathon.goodbyebunny.domain.member.dao.MemberRepository;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.clova.hackathon.goodbyebunny.domain.member.model.QMember.member;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final MemberRepository memberRepository;
    private final CustomItemRepositoryImpl customItemRepository;
    private final ItemRepository itemRepository;
    private final MemberItemRepository memberItemRepository;
    private final PickedItemRepository pickedItemRepository;

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

    @Transactional
    public void pickItem(PickItemRequest request, String nickname) {
        Member member = memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));
        Item item = itemRepository.findItemByItemNumAndType(request.getItemNum(), request.getType())
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다."));

        MemberItem memberItem = memberItemRepository.findMemberItemByMemberAndItem(member,item)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 안가지고 있습니다."));
        
        Optional<PickedItem> pickedItem = pickedItemRepository.findPickedItemByMember(member);
        if (pickedItem.isPresent()){
            if (request.getType().equals(ItemType.DRESS)){
                pickedItem.get().update_dress_id(request.getItemNum());
            }else{
                pickedItem.get().update_background_id(request.getItemNum());
            }
            pickedItemRepository.save(pickedItem.get());
        }else{
            if (request.getType().equals(ItemType.DRESS)){
                pickedItemRepository.save(PickedItem.builder()
                        .member(member)
                        .dress_id(request.getItemNum())
                        .build());
            }else{
                pickedItemRepository.save(PickedItem.builder()
                        .member(member)
                        .background_id(request.getItemNum())
                        .build());
            }
        };
    }

    public GetItemResponseDto getItem(String nickname){
        Member member = memberRepository.findMemberByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));
        Optional<PickedItem> pickedItem = pickedItemRepository.findPickedItemByMember(member);

        return pickedItem.map(item -> new GetItemResponseDto(item.getBackground_id(), item.getDress_id())).orElseGet(() -> new GetItemResponseDto(0, 0));
    }
}
