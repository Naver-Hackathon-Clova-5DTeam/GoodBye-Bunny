package com.clova.hackathon.goodbyebunny.domain.member.model;

import com.clova.hackathon.goodbyebunny.domain.comment.model.Comment;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "member_id"))
@AttributeOverride(name = "createdDate", column = @Column(name = "member_created_date"))
@AttributeOverride(name = "updatedDate", column = @Column(name = "member_updated_date"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {



    @NonNull
    private String nickname;

    @NonNull
    private String password;

    @NonNull
    private int age;

    @Column(name=" comment_num")
    private int commentNum;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Review review;
}
