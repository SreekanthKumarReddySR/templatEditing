package com.classplus.greetings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageOverlayRequest {
    private String templateId;
    private String userName;
    private String userProfileImageUrl;
    private String customText; // optional custom message
}
