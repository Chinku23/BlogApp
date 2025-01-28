package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	public CategoryDto createCategory(CategoryDto categoryDto);
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	public void deleteCategory(Integer categoryId);
	public CategoryDto getCategoryById(Integer categoryId);
	public List<CategoryDto> allCategories();
	

}
