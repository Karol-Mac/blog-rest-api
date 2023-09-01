package com.blog.service;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPost(long id);
    PostDto updatePost(PostDto postDto, long id);

    String deletePostById(long id);

    List<PostDto> getPostsByCategory(long categoryId);

}
