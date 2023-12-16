package com.clova.hackathon.goodbyebunny.domain.comment.dao;

import com.clova.hackathon.goodbyebunny.domain.comment.model.Comment;
import com.clova.hackathon.goodbyebunny.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByReviewId(Long reviewId);

    Optional<List<Comment>> findCommentByMember(Member member);
}