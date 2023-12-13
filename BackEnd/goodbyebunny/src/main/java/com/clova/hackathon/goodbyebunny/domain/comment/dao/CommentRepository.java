package com.clova.hackathon.goodbyebunny.domain.comment.dao;

import com.clova.hackathon.goodbyebunny.domain.comment.model.Comment;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import com.clova.hackathon.goodbyebunny.domain.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByReviewId(Long reviewId);
}