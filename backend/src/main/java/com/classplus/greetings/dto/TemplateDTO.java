package com.classplus.greetings.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateDTO {
    private String id;
    private String title;
    private String category;
    private String imageUrl;
    private String imageThumbnailUrl;
    private boolean isPremium;
    private String description;
    private int likes;
    private int shares;
    private LocalDateTime createdAt;
}
