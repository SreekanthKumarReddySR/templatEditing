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
@Document(collection = "templates")
public class Template {
    
    @Id
    private String id;
    
    private String title;
    private String category; // BIRTHDAY, ANNIVERSARY, FESTIVAL, CONGRATULATION, etc.
    private String imageUrl;
    private String imageThumbnailUrl;
    private boolean isPremium;
    private String description;
    private int likes;
    private int shares;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Category {
        BIRTHDAY,
        ANNIVERSARY,
        FESTIVAL,
        CONGRATULATION,
        LOVE,
        GET_WELL_SOON,
        THANK_YOU,
        PROMOTION,
        RETIREMENT,
        GRADUATION
    }
}
