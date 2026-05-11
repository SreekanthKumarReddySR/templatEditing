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
@Document(collection = "subscriptions")
public class Subscription {
    
    @Id
    private String id;
    
    private String userId;
    private String planType; // MONTHLY, ANNUAL
    private double amount;
    private String currency;
    private String paymentMethod; // CARD, UPI, PAYPAL
    private String transactionId;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
    private String status; // ACTIVE, EXPIRED, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
