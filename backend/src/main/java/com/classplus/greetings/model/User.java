package com.classplus.greetings.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    
    @Id
    private String id;
    
    private String name;
    private String email;
    private String password;
    private String profilePictureUrl;
    private String authProvider; // GOOGLE, EMAIL, GUEST
    private String googleId;
    private SubscriptionTier subscriptionTier; // FREE, PREMIUM
    private LocalDateTime subscriptionExpiryDate;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum SubscriptionTier {
        FREE, PREMIUM
    }
}
