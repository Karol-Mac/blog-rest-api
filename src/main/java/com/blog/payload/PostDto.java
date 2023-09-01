package com.blog.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(
        description = "PostDto  model Information"
)
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min = 2, message = "title should have at least 2 characters")
    @Schema(
            description = "Title of Blog Post"
    )
    private String title;

    @NotEmpty
    @Size(min=10, message = "description should have at least 10 characters")
    @Schema(
            description = "Description of Blog Post"
    )
    private String description;

    @NotEmpty
    @Schema(
            description = "Content of Blog Post"
    )
    private String content;
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog post Category (type)"
    )
    private long categoryId;
}