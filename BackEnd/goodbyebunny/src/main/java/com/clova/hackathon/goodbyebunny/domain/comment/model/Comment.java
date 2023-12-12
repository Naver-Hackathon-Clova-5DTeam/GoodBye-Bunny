package com.clova.hackathon.goodbyebunny.domain.comment.model;

import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.*;
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

    @NonNull
    @Column(name = "comment_content")
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


}
