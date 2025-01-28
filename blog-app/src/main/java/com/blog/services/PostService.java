package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
	//create
   PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
   //update
   PostDto updatePost(PostDto postDto, Integer postId);
   //delete
   void deletePost(Integer postId);
   //all post
   PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
   //show post
   PostDto getPostById(Integer postId);
   //all post by category
   List<PostDto> getPostByCategory(Integer categoryId);
   //all post by user
   List<PostDto> getPostByUser(Integer userId);
   
   //search posts
   List<PostDto> searchPosts(String keyword);
}
