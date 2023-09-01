package com.blog.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
