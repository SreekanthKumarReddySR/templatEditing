package com.classplus.greetings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageOverlayResponse {
    private String greetingId;
    private String personalizedImageUrl;
    private String templateId;
    private String message;
}
