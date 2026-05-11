package com.classplus.greetings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareGreetingRequest {
    
    @NotBlank(message = "Greeting ID is required")
    private String greetingId;
    
    @NotBlank(message = "Share platform is required")
    private String platform; // WHATSAPP, INSTAGRAM, EMAIL, TWITTER, SMS
    
    private String recipientEmail;
    private String recipientPhone;
    private String customMessage;
}
