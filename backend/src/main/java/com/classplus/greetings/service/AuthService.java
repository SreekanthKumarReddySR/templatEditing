package com.classplus.greetings.service;

import com.classplus.greetings.dto.RegisterRequest;
import com.classplus.greetings.dto.LoginRequest;
import com.classplus.greetings.dto.AuthResponse;
import com.classplus.greetings.dto.GoogleAuthRequest;
import com.classplus.greetings.model.User;
import com.classplus.greetings.repository.UserRepository;
import com.classplus.greetings.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setProfilePictureUrl(request.getProfilePictureUrl());
        user.setAuthProvider("EMAIL");
        user.setSubscriptionTier(User.SubscriptionTier.FREE);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getId());

        String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        log.info("User logged in successfully: {}", user.getId());

        return AuthResponse.builder()
                .token(token)
                .message("User logged in successfully")
                .build();
    }

    public AuthResponse googleAuth(GoogleAuthRequest request) {
        Optional<User> userOptional = userRepository.findByGoogleId(request.getEmail());

        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user.setUpdatedAt(LocalDateTime.now());
            user = userRepository.save(user);
            log.info("Google user logged in: {}", user.getId());
        } else {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already registered with different provider");
            }

            user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setGoogleId(request.getEmail());
            user.setProfilePictureUrl(request.getProfilePictureUrl());
            user.setAuthProvider("GOOGLE");
            user.setSubscriptionTier(User.SubscriptionTier.FREE);
            user.setActive(true);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            user = userRepository.save(user);
            log.info("New Google user registered: {}", user.getId());
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("Google authentication successful")
                .build();
    }

    public AuthResponse guestLogin() {
        User user = new User();
        user.setName("Guest_" + System.currentTimeMillis());
        user.setEmail("guest_" + System.currentTimeMillis() + "@classplus.local");
        user.setAuthProvider("GUEST");
        user.setSubscriptionTier(User.SubscriptionTier.FREE);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user = userRepository.save(user);
        log.info("Guest user created: {}", user.getId());

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .message("Guest login successful")
                .build();
    }
}
