package com.blog.payload;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    @NotEmpty
    @Size(min=2, message = "name should have at least 2 characters")
    private String name;

    @Nullable
    String email;

    @NotEmpty
    @Size(min=10, message = "comment must contain at least 10 characters")
    private String body;
}
