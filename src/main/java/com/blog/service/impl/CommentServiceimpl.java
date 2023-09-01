package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.exception.BlogApiException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.payload.Magazine;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class CommentServiceimpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    public CommentServiceimpl(CommentRepository commentRepository,
                              PostRepository postRepository, UserRepository userRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto, Principal principal) {

        String email = principal.getName();
        User user = userRepository.findByUsernameOrEmail(email, email).orElseThrow(
                () -> new UsernameNotFoundException("User with this credentials does not exist")
        );

        //map commentDto to comment
        Comment comment = mapToDEntity(commentDto);
        comment.setEmail(user.getEmail());

        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsRelatedToPost(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        //convert list of comments to list of commentsDto's
        return comments.stream().map(this::mapToDto).toList();
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Magazine magazine = commentBelongToPost(postId, commentId);

        return magazine.belong() ? mapToDto(magazine.comment()) : null;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        //checking if comment belong to post
        Magazine magazine = commentBelongToPost(postId, commentId);

        //get comment from magazine
        Comment comment = magazine.comment();

        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());

        return mapToDto(commentRepository.save(comment));

    }

    @Override
    public String deleteComment(long postId, long commentId) {
        //checking if comment belong to post
        Magazine magazine = commentBelongToPost(postId, commentId);
        Comment comment = magazine.comment();

        commentRepository.delete(comment);
        return "Comment deleted successfully";
    }


    private CommentDto mapToDto(Comment comment){
        return mapper.map(comment, CommentDto.class);
    }

    private Comment mapToDEntity(CommentDto commentDto){
        return mapper.map(commentDto, Comment.class);
    }

    private Magazine commentBelongToPost(long postId, long commentId){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException( HttpStatus.BAD_REQUEST, "comment does not belong to this post");

        return new Magazine(post, comment, true);
    }

}
