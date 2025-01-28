package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {
   
	CommentDto creatComment(CommentDto commentDto, Integer postId);
	
	void deleteComment(Integer commentId);
	
}
