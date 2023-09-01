package com.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String name;

    @Size(min=1, message = "username has to be larger than 0")
    private String username;

    @Email(message = "email must contain @")
    private String email;
    private String password;
}
