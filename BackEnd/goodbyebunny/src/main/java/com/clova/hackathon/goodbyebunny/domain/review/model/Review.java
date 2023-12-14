package com.clova.hackathon.goodbyebunny.domain.review.model;

import com.clova.hackathon.goodbyebunny.domain.comment.model.Comment;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "review")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "review_id"))
@AttributeOverride(name = "createdDate", column = @Column(name = "review_created_date"))
@AttributeOverride(name = "updatedDate", column = @Column(name = "review_updated_date"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewKeyword> reviewKeywords = new ArrayList<>();

    public void updateReview(String reviewContent){
        this.content = reviewContent;
    }

}

