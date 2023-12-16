package com.clova.hackathon.goodbyebunny.domain.comment.api;

import com.clova.hackathon.goodbyebunny.domain.comment.api.request.CommentCreateRequest;
import com.clova.hackathon.goodbyebunny.domain.comment.api.response.CommentReadResponse;
import com.clova.hackathon.goodbyebunny.domain.comment.app.CommentService;
import com.clova.hackathon.goodbyebunny.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PostMapping("/{review_id}")
    public ResponseEntity<Long> createComment(@AuthenticationPrincipal final MemberDetails member,
            @PathVariable("review_id") Long reviewId,@RequestBody CommentCreateRequest request) {


        String memberNickname =member.getUsername();
        return commentService.createComment(memberNickname,reviewId,request);
    }

    @GetMapping("/{review_id}/comment")
    public ResponseEntity<List<CommentReadResponse>> getReview(@PathVariable("review_id") Long reviewId) {

        return commentService.getComments(reviewId);
    }

    @GetMapping("")
    public ResponseEntity<?> getCommentList(@AuthenticationPrincipal final MemberDetails member){
        return commentService.getCommentList(member.getNickname());
    }
}
