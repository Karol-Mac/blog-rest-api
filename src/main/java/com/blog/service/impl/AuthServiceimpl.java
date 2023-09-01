package com.blog.service.impl;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exception.BlogApiException;
import com.blog.payload.LoginDto;
import com.blog.payload.RegisterDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;
import com.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceimpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceimpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {

        //check if username exists in database
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "User with this username already exists.");
        }

        //check if email exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "User with this email already exists.");
        }

        //parsing register data to user (entity)
        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));


        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER").get());
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully!.";
    }
}
