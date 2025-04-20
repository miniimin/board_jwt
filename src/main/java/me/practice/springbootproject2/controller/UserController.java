package me.practice.springbootproject2.controller;

import lombok.RequiredArgsConstructor;
import me.practice.springbootproject2.dto.*;
import me.practice.springbootproject2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Long> newUser(@RequestBody AddUserRequest request) {
        Long userId = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshAccessToken(@RequestBody RefreshRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.refresh(request));
    }
}
