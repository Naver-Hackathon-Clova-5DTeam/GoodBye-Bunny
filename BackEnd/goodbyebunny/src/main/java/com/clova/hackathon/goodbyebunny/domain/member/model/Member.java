package com.clova.hackathon.goodbyebunny.domain.member.model;

import com.clova.hackathon.goodbyebunny.domain.comment.model.Comment;
import com.clova.hackathon.goodbyebunny.domain.item.model.Item;
import com.clova.hackathon.goodbyebunny.domain.item.model.MemberItem;
import com.clova.hackathon.goodbyebunny.domain.item.model.PickedItem;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonSerialize
public class Member extends BaseEntity {
    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    private int age;

    @Column(name=" comment_num")
    private int commentNum;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Review review;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private PickedItem pickedItem;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberItem> item;

    private int point;

    private String profile;

    public void updateMember(final int point) {
        this.point = point;
    }
}
