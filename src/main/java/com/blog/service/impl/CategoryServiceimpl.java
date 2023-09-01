package com.blog.service.impl;

import com.blog.entity.Category;
import com.blog.exception.BlogApiException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceimpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    public CategoryServiceimpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);

        Category savedCategory = categoryRepository.save(category);

        return mapToDto(savedCategory);
    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        return mapToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryId);

        Category updatedCategory = categoryRepository.save(category);

        return mapToDto(updatedCategory);
    }

    @Override
    public String deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

        return "Category deleted";
    }

    Category mapToEntity(CategoryDto categoryDto){
        return mapper.map(categoryDto, Category.class);
    }

    CategoryDto mapToDto(Category category){
        return mapper.map(category, CategoryDto.class);
    }

}
