package com.blog.controller;

import com.blog.payload.JWTAuthResponse;
import com.blog.payload.LoginDto;
import com.blog.payload.RegisterDto;
import com.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name = "Crud REST API for Authorization Resource"
)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Login REST API
    @PostMapping(value = {"/login", "signin"})
    @Operation(
            summary = "Login/sign-in existing User REST API",
            description = "Login/sign-in existing User REST API - used generate JWT token for " +
                    "User while login to REST API"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){

        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    @Operation(
            summary = "Create new User REST API",
            description = "Create new User REST API - used to add new User to DB"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto){
        return new ResponseEntity(authService.register(registerDto), HttpStatus.CREATED);
    }

}
