package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.PostService;
import com.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name = "Crud REST API for Post Resource"
)
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API - used to save post in DB"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all posts
    @GetMapping
    @Operation(
            summary = "Get all Posts REST API",
            description = "Get all Posts REST API - used to fetch all posts from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public PostResponse getAllPosts(
            @RequestParam(defaultValue = AppConstants.DEF_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEF_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEF_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEF_SORT_ORDER, required = false) String sortDir
            ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //get post by id
    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Get Post by Id REST API",
            description = "Get Post by Id REST API - used to get single post (by Id) from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    //upgrade existing post (by id)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API - update particular post in DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable long id){
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    //delete post
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API - delete post (by ID) from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<String> deletePost(@PathVariable long id){
        return ResponseEntity.ok(postService.deletePostById(id));
    }

    //get posts by category (id)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable long categoryId){
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }

}
