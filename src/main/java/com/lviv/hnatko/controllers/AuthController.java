package com.lviv.hnatko.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lviv.hnatko.dto.JwtAuthenticationResponse;
import com.lviv.hnatko.dto.LoginRequest;
import com.lviv.hnatko.dto.UserRequest;
import com.lviv.hnatko.dto.UserResponse;
import com.lviv.hnatko.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<UserResponse> register(@RequestBody UserRequest userRequest) {

        return new ResponseEntity<>(authService.registerUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {

        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }
}
