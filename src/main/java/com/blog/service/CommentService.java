package com.blog.service;

import com.blog.payload.CommentDto;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto, Principal principal);

    List<CommentDto> getCommentsRelatedToPost(long postId);

    CommentDto getCommentById(long postId, long  commentId);
    CommentDto updateComment(long postId, long  commentId, CommentDto commentDto);

    String deleteComment(long postId, long  commentId);
}
