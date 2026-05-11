package com.classplus.greetings.controller;

import com.classplus.greetings.dto.*;
import com.classplus.greetings.service.AuthService;
import com.classplus.greetings.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<AuthResponse>builder()
                            .success(true)
                            .message("User registered successfully")
                            .data(response)
                            .timestamp(System.currentTimeMillis())
                            .build());
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.<AuthResponse>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                    .success(true)
                    .message("Login successful")
                    .data(response)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.<AuthResponse>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @PostMapping("/google")
    public ResponseEntity<ApiResponse<AuthResponse>> googleAuth(@Valid @RequestBody GoogleAuthRequest request) {
        try {
            AuthResponse response = authService.googleAuth(request);
            return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                    .success(true)
                    .message("Google authentication successful")
                    .data(response)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Google auth error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.<AuthResponse>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @PostMapping("/guest")
    public ResponseEntity<ApiResponse<AuthResponse>> guestLogin() {
        try {
            AuthResponse response = authService.guestLogin();
            return ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                    .success(true)
                    .message("Guest login successful")
                    .data(response)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Guest login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<AuthResponse>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(HttpServletRequest request) {
        try {
            String userId = (String) request.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<UserDTO>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }
            UserDTO user = userService.getUserById(userId);
            return ResponseEntity.ok(ApiResponse.<UserDTO>builder()
                    .success(true)
                    .message("User retrieved successfully")
                    .data(user)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get user error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<UserDTO>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }
}
