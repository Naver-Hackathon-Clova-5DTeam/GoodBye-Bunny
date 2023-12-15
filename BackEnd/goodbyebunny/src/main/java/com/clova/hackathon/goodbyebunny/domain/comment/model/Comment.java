package com.clova.hackathon.goodbyebunny.domain.comment.model;

import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Getter
@Table(name = "comment")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
@AttributeOverride(name = "createdDate", column = @Column(name = "comment_created_date"))
@AttributeOverride(name = "updatedDate", column = @Column(name = "comment_updated_date"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @NotNull
    @Size(min=1,max=300)
    @Column(name = "comment_content",length = 300)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
