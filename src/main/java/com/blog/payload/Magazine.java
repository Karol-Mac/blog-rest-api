package com.blog.payload;

import com.blog.entity.Comment;
import com.blog.entity.Post;

public record Magazine(Post post, Comment comment, boolean belong) {
}
