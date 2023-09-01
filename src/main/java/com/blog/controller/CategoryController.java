package com.blog.controller;

import com.blog.payload.CategoryDto;
import com.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(
        name = "Crud REST API for Category Resource"
)
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API - used to save category in DB"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get all Categories REST API",
            description = "Get all Categories REST API - used to fetch all categories from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<List<CategoryDto>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{categoryId}")
    @Operation(
            summary = "Get Category by Id REST API",
            description = "Get Category by Id REST API - used to get single category (by Id) from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long categoryId){
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Update Category REST API",
            description = "Update Category REST API - update particular category in DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long categoryId,
                                                      @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, categoryId));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API - delete category (by ID) from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }

}
