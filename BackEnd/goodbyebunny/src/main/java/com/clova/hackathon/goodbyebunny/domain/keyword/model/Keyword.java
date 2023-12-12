package com.clova.hackathon.goodbyebunny.domain.keyword.model;


import com.clova.hackathon.goodbyebunny.domain.review.model.ReviewKeyword;
import com.clova.hackathon.goodbyebunny.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "keyword")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "keyword_id"))
@AttributeOverride(name = "createdDate", column = @Column(name = "keyword_created_date"))
@AttributeOverride(name = "updatedDate", column = @Column(name = "keyword_updated_date"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseEntity {

    @NonNull
    private String word;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<ReviewKeyword> reviewKeywords = new ArrayList<>();

}
