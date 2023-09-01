package com.blog.service.impl;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceimpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    public PostServiceimpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );

        //convert DTO to entity
        //saving entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        //convert entity to DTO

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        //create Pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //getting content from page obj:
        List<Post> listOfPosts = posts.toList();
        List<PostDto> content = listOfPosts.stream().map(this::mapToDto).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId())
        );

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updated = postRepository.save(post);
        return mapToDto(updated);
    }

    @Override
    public String deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));

        postRepository.delete(post);

        return "Post deleted successfully.";
    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        List<Post> posts = postRepository.findByCategoryId(category.getId());

        return posts.stream().map(this::mapToDto).toList();

    }

    private PostDto mapToDto(Post post){
        return mapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto){
        return mapper.map(postDto, Post.class);
    }
}
