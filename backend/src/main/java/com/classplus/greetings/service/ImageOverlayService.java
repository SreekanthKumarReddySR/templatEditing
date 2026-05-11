package com.classplus.greetings.service;

import com.classplus.greetings.dto.ImageOverlayRequest;
import com.classplus.greetings.dto.ImageOverlayResponse;
import com.classplus.greetings.model.SharedGreeting;
import com.classplus.greetings.model.Template;
import com.classplus.greetings.repository.SharedGreetingRepository;
import com.classplus.greetings.repository.TemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ImageOverlayService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private SharedGreetingRepository sharedGreetingRepository;

    /**
     * Creates a personalized greeting by overlaying user profile picture and name on template
     * Note: In production, this would use actual image processing. For now, we're storing
     * the configuration and letting frontend handle the overlay using Canvas API
     */
    public ImageOverlayResponse createPersonalizedGreeting(String userId, ImageOverlayRequest request) {
        Optional<Template> templateOptional = templateRepository.findById(request.getTemplateId());
        if (templateOptional.isEmpty()) {
            throw new RuntimeException("Template not found");
        }

        Template template = templateOptional.get();

        // Create shared greeting record
        SharedGreeting greeting = new SharedGreeting();
        greeting.setId(UUID.randomUUID().toString());
        greeting.setUserId(userId);
        greeting.setTemplateId(request.getTemplateId());
        greeting.setPersonalizationData(String.format("{\"userName\":\"%s\",\"userProfileImageUrl\":\"%s\",\"customText\":\"%s\"}",
                request.getUserName(),
                request.getUserProfileImageUrl(),
                request.getCustomText() != null ? request.getCustomText() : ""
        ));
        greeting.setSharedAt(LocalDateTime.now());

        // Generate personalized image URL (in production, this would be the processed image URL)
        String personalizedImageUrl = generatePersonalizedImageUrl(template, request);
        greeting.setPersonalizedImageUrl(personalizedImageUrl);

        SharedGreeting savedGreeting = sharedGreetingRepository.save(greeting);
        log.info("Personalized greeting created: {}", savedGreeting.getId());

        return ImageOverlayResponse.builder()
                .greetingId(savedGreeting.getId())
                .personalizedImageUrl(personalizedImageUrl)
                .templateId(request.getTemplateId())
                .message("Greeting created successfully")
                .build();
    }

    /**
     * Generates personalized image URL
     * In production, this would:
     * 1. Download the template image
     * 2. Load user profile picture
     * 3. Use ImageMagick/FFmpeg to overlay and composite images
     * 4. Upload to cloud storage (Cloudinary)
     * 5. Return the URL
     */
    private String generatePersonalizedImageUrl(Template template, ImageOverlayRequest request) {
        // Simulated URL - in production, would use actual image processing
        return String.format("https://greetings-api.classplus.local/greeting/%s/preview?user=%s&template=%s",
                UUID.randomUUID().toString(),
                encodeParam(request.getUserName()),
                template.getId()
        );
    }

    private String encodeParam(String param) {
        return param.replaceAll("\\s+", "_");
    }

    public SharedGreeting getSharedGreeting(String greetingId) {
        Optional<SharedGreeting> greetingOptional = sharedGreetingRepository.findById(greetingId);
        if (greetingOptional.isEmpty()) {
            throw new RuntimeException("Greeting not found");
        }
        return greetingOptional.get();
    }

    public void incrementViewCount(String greetingId) {
        Optional<SharedGreeting> greetingOptional = sharedGreetingRepository.findById(greetingId);
        if (greetingOptional.isPresent()) {
            SharedGreeting greeting = greetingOptional.get();
            greeting.setViewCount(greeting.getViewCount() + 1);
            sharedGreetingRepository.save(greeting);
        }
    }
}
