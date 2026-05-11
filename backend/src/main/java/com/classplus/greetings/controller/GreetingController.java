package com.classplus.greetings.controller;

import com.classplus.greetings.dto.*;
import com.classplus.greetings.model.SharedGreeting;
import com.classplus.greetings.service.ImageOverlayService;
import com.classplus.greetings.service.SharedGreetingService;
import com.classplus.greetings.service.TemplateService;
import com.classplus.greetings.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/greetings")
@CrossOrigin(origins = "*")
public class GreetingController {

    @Autowired
    private ImageOverlayService imageOverlayService;

    @Autowired
    private SharedGreetingService sharedGreetingService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private TemplateService templateService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ImageOverlayResponse>> createPersonalizedGreeting(
            @Valid @RequestBody ImageOverlayRequest request,
            HttpServletRequest httpRequest) {
        try {
            String userId = (String) httpRequest.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<ImageOverlayResponse>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            // Check if template is premium and user doesn't have access
            TemplateDTO template = templateService.getTemplateById(request.getTemplateId());
            if (template.isPremium() && !subscriptionService.isUserPremium(userId)) {
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                        .body(ApiResponse.<ImageOverlayResponse>builder()
                                .success(false)
                                .error("Premium subscription required to use this template")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            ImageOverlayResponse response = imageOverlayService.createPersonalizedGreeting(userId, request);
            templateService.incrementLikes(request.getTemplateId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<ImageOverlayResponse>builder()
                            .success(true)
                            .message("Greeting created successfully")
                            .data(response)
                            .timestamp(System.currentTimeMillis())
                            .build());
        } catch (Exception e) {
            log.error("Create greeting error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<ImageOverlayResponse>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @PostMapping("/share")
    public ResponseEntity<ApiResponse<SharedGreeting>> shareGreeting(
            @Valid @RequestBody ShareGreetingRequest request,
            HttpServletRequest httpRequest) {
        try {
            String userId = (String) httpRequest.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<SharedGreeting>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            SharedGreeting greeting = sharedGreetingService.shareGreeting(userId, request);
            return ResponseEntity.ok(ApiResponse.<SharedGreeting>builder()
                    .success(true)
                    .message("Greeting shared successfully")
                    .data(greeting)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Share greeting error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<SharedGreeting>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/my-greetings")
    public ResponseEntity<ApiResponse<List<SharedGreeting>>> getUserGreetings(HttpServletRequest httpRequest) {
        try {
            String userId = (String) httpRequest.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<List<SharedGreeting>>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            List<SharedGreeting> greetings = sharedGreetingService.getUserSharedGreetings(userId);
            return ResponseEntity.ok(ApiResponse.<List<SharedGreeting>>builder()
                    .success(true)
                    .message("Greetings retrieved successfully")
                    .data(greetings)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get user greetings error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<SharedGreeting>>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/{greetingId}")
    public ResponseEntity<ApiResponse<SharedGreeting>> getGreeting(@PathVariable String greetingId) {
        try {
            SharedGreeting greeting = imageOverlayService.getSharedGreeting(greetingId);
            imageOverlayService.incrementViewCount(greetingId);
            return ResponseEntity.ok(ApiResponse.<SharedGreeting>builder()
                    .success(true)
                    .message("Greeting retrieved successfully")
                    .data(greeting)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get greeting error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<SharedGreeting>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/share-link/{greetingId}")
    public ResponseEntity<ApiResponse<String>> getShareLink(@PathVariable String greetingId) {
        try {
            String shareLink = sharedGreetingService.generateShareLink(greetingId);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .success(true)
                    .message("Share link generated successfully")
                    .data(shareLink)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get share link error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }
}
