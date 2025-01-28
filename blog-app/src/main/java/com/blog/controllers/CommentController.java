package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	private CommentService commentService;
    
    // Create a comment
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId) {
        CommentDto createdComment = this.commentService.creatComment(commentDto, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Delete a comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("comment is deleted", true), HttpStatus.OK);
    }

}
