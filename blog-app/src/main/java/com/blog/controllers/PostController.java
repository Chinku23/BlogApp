package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.modelmapper.internal.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.payloads.UserDto;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId) {

        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    //show post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
    	 PostDto post = this.postService.getPostById(postId); 
 		 return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }
    
    //list of post
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(
    		             @RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)  Integer pageNumber,
    		             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_Size, required = false) Integer pageSize,
    		             @RequestParam(value= "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
    		             @RequestParam(value= "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir ){
    	
    	PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
    	return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
    
    //all post by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
    	List<PostDto> cat = this.postService.getPostByCategory(categoryId);
    	return new ResponseEntity<>(cat, HttpStatus.OK);
    }
    
//    all post by user
    
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> u1 = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(u1, HttpStatus.OK);
    }
    
 // Update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // Delete post
    @DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
	    return new ResponseEntity<>(new ApiResponse("Post is deleted", true), HttpStatus.OK);
	}
    
    //search method
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keywords) {
    	List<PostDto> searchPosts = this.postService.searchPosts(keywords);
    	return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
    }
    
    //post Image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
    	String fileName = this.fileService.uploadImage(path, image);
    	PostDto postDto = this.postService.getPostById(postId);
    	postDto.setImageName(fileName);
    	PostDto updatePost = this.postService.updatePost(postDto, postId);
    	return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }
    //serve files
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
