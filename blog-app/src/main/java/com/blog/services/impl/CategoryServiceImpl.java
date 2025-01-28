package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.User;
import com.blog.expections.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.payloads.UserDto;
import com.blog.repositories.CategoryRepo;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
    private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
 
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(cat);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Categor", "Category Id", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Categor", "Category Id", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> allCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		return categories.stream().map(cat -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
	}

}
