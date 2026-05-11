package com.classplus.greetings.service;

import com.classplus.greetings.dto.ShareGreetingRequest;
import com.classplus.greetings.model.SharedGreeting;
import com.classplus.greetings.repository.SharedGreetingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SharedGreetingService {

    @Autowired
    private SharedGreetingRepository sharedGreetingRepository;

    @Autowired
    private TemplateService templateService;

    public SharedGreeting shareGreeting(String userId, ShareGreetingRequest request) {
        Optional<SharedGreeting> greetingOptional = sharedGreetingRepository.findById(request.getGreetingId());
        if (greetingOptional.isEmpty()) {
            throw new RuntimeException("Greeting not found");
        }

        SharedGreeting greeting = greetingOptional.get();
        if (!greeting.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only share your own greetings");
        }

        greeting.setSharedWith(request.getPlatform());
        
        if ("EMAIL".equalsIgnoreCase(request.getPlatform())) {
            greeting.setRecipientInfo(request.getRecipientEmail());
            // In production: Send email with greeting link
            log.info("Email share requested to: {}", request.getRecipientEmail());
        } else if ("WHATSAPP".equalsIgnoreCase(request.getPlatform())) {
            greeting.setRecipientInfo(request.getRecipientPhone());
            // In production: Generate WhatsApp share link
            log.info("WhatsApp share requested");
        } else if ("SMS".equalsIgnoreCase(request.getPlatform())) {
            greeting.setRecipientInfo(request.getRecipientPhone());
            // In production: Send SMS with greeting link
            log.info("SMS share requested to: {}", request.getRecipientPhone());
        } else {
            // INSTAGRAM, TWITTER, FACEBOOK - Generate share links
            log.info("Social share requested for: {}", request.getPlatform());
        }

        templateService.incrementShares(greeting.getTemplateId());
        SharedGreeting savedGreeting = sharedGreetingRepository.save(greeting);
        
        log.info("Greeting shared successfully: {} via {}", request.getGreetingId(), request.getPlatform());
        return savedGreeting;
    }

    public List<SharedGreeting> getUserSharedGreetings(String userId) {
        return sharedGreetingRepository.findByUserIdOrderBySharedAtDesc(userId);
    }

    public String generateShareLink(String greetingId) {
        return String.format("https://classplus-greetings.render.com/share/%s", greetingId);
    }

    public String getWhatsAppShareUrl(String greetingId, String message) {
        String shareLink = generateShareLink(greetingId);
        String text = String.format("%s %s", message, shareLink);
        return String.format("https://wa.me/?text=%s", encodeUrl(text));
    }

    public String getEmailShareUrl(String greetingId, String recipientEmail) {
        String shareLink = generateShareLink(greetingId);
        return String.format("mailto:%s?subject=Check out my custom greeting&body=%s", 
                recipientEmail, encodeUrl(shareLink));
    }

    public String getTwitterShareUrl(String greetingId, String message) {
        String shareLink = generateShareLink(greetingId);
        String text = String.format("%s %s", message, shareLink);
        return String.format("https://twitter.com/intent/tweet?text=%s", encodeUrl(text));
    }

    private String encodeUrl(String url) {
        try {
            return java.net.URLEncoder.encode(url, "UTF-8");
        } catch (Exception e) {
            return url;
        }
    }
}
