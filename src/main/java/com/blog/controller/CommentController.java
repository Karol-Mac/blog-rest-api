package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Crud REST API for Comment Resource"
)
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST API - used to save comment in DB"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId,
                                                    @RequestBody @Valid CommentDto commentDto, Principal principal){
        return new ResponseEntity<>(
                commentService.createComment(postId, commentDto, principal), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    @Operation(
            summary = "Get all Comments REST API",
            description = "Get all Comments REST API - used to fetch all comments from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public List<CommentDto> getCommentsByPostId(@PathVariable long postId){
        return commentService.getCommentsRelatedToPost(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    @Operation(
            summary = "Get Comment by Id REST API",
            description = "Get Comment by Id REST API - used to get single comment (by Id) from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<CommentDto> getCommentByPostId(
                    @PathVariable long postId, @PathVariable long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @Operation(
            summary = "Update Comment REST API",
            description = "Update Comment REST API - update particular comment in DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<CommentDto> updateComment(@PathVariable long postId,
                                    @PathVariable long commentId, @RequestBody @Valid CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto) );
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @Operation(
            summary = "Delete Comment REST API",
            description = "Delete Comment REST API - delete comment (by ID) from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<String> deleteComment(@PathVariable long postId,
                                                    @PathVariable long commentId){
        return ResponseEntity.ok(commentService.deleteComment(postId, commentId));
    }

}
