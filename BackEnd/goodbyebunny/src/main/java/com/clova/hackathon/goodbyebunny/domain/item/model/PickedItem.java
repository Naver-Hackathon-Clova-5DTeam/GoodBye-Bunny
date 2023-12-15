package com.clova.hackathon.goodbyebunny.domain.item.model;


import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PickedItem extends BaseEntity {


    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "int default 0")
    private int dress_id;

    @Column(columnDefinition = "int default 0")
    private int background_id;

    public void update_dress_id(int dress_id) {
        this.dress_id = dress_id;
    }

    public void update_background_id(int background_id) {
        this.background_id = background_id;
    }

}
