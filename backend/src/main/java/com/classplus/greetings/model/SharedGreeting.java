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
@Document(collection = "shared_greetings")
public class SharedGreeting {
    
    @Id
    private String id;
    
    private String userId;
    private String templateId;
    private String personalizationData; // JSON string containing overlay data
    private String personalizedImageUrl;
    private String sharedWith; // EMAIL, WHATSAPP, INSTAGRAM, TWITTER, EMAIL
    private String recipientInfo;
    private int viewCount;
    private LocalDateTime sharedAt;
    private LocalDateTime expiryDate;
}
