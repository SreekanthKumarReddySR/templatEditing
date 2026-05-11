package com.classplus.greetings.service;

import com.classplus.greetings.dto.UserDTO;
import com.classplus.greetings.model.User;
import com.classplus.greetings.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return mapToDTO(userOptional.get());
    }

    public UserDTO getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return mapToDTO(userOptional.get());
    }

    public UserDTO updateUserProfile(String userId, UserDTO updateRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (updateRequest.getName() != null) {
            user.setName(updateRequest.getName());
        }
        if (updateRequest.getProfilePictureUrl() != null) {
            user.setProfilePictureUrl(updateRequest.getProfilePictureUrl());
        }

        User updatedUser = userRepository.save(user);
        log.info("User profile updated: {}", userId);

        return mapToDTO(updatedUser);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profilePictureUrl(user.getProfilePictureUrl())
                .subscriptionTier(user.getSubscriptionTier().toString())
                .subscriptionExpiryDate(user.getSubscriptionExpiryDate())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
