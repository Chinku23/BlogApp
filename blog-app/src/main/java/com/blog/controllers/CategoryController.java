package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto category = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
	{
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
	{
		this.categoryService.deleteCategory(catId);
	    return new ResponseEntity<>(new ApiResponse("Category is deleted", true), HttpStatus.OK);
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer catId) {
		CategoryDto category = this.categoryService.getCategoryById(catId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}
	
	@GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> allCategories = this.categoryService.allCategories();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }
	
}
