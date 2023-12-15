package com.clova.hackathon.goodbyebunny.domain.review.dao;

import com.clova.hackathon.goodbyebunny.domain.review.model.ReviewKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewKeywordRepository extends JpaRepository<ReviewKeyword, Long> {

}
