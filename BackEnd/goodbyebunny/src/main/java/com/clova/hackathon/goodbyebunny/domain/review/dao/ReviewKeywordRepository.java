package com.clova.hackathon.goodbyebunny.domain.review.dao;

import com.clova.hackathon.goodbyebunny.domain.review.model.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {
    Optional<List<ReviewKeyword>> findReviewKeywordsByReviewId(Long reviewId);


}
