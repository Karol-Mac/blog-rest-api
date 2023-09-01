package com.blog.service;


import com.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    List<CategoryDto> getCategories();

    CategoryDto getCategory(long categoryId);
    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);

    String deleteCategory(long categoryId);

}
