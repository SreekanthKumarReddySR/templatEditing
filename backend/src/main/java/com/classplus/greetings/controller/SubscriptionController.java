package com.classplus.greetings.controller;

import com.classplus.greetings.dto.ApiResponse;
import com.classplus.greetings.model.Subscription;
import com.classplus.greetings.service.SubscriptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(
            @RequestParam String planType,
            HttpServletRequest request) {
        try {
            String userId = (String) request.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<Subscription>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            Subscription subscription = subscriptionService.createSubscription(userId, planType);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.<Subscription>builder()
                            .success(true)
                            .message("Subscription created successfully")
                            .data(subscription)
                            .timestamp(System.currentTimeMillis())
                            .build());
        } catch (Exception e) {
            log.error("Create subscription error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Subscription>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/my-subscription")
    public ResponseEntity<ApiResponse<Subscription>> getSubscription(HttpServletRequest request) {
        try {
            String userId = (String) request.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<Subscription>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            Subscription subscription = subscriptionService.getSubscriptionByUserId(userId);
            if (subscription == null) {
                return ResponseEntity.ok(ApiResponse.<Subscription>builder()
                        .success(true)
                        .message("No active subscription found")
                        .data(null)
                        .timestamp(System.currentTimeMillis())
                        .build());
            }

            return ResponseEntity.ok(ApiResponse.<Subscription>builder()
                    .success(true)
                    .message("Subscription retrieved successfully")
                    .data(subscription)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get subscription error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<Subscription>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<String>> cancelSubscription(HttpServletRequest request) {
        try {
            String userId = (String) request.getAttribute("userId");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.<String>builder()
                                .success(false)
                                .error("Unauthorized")
                                .timestamp(System.currentTimeMillis())
                                .build());
            }

            subscriptionService.cancelSubscription(userId);
            return ResponseEntity.ok(ApiResponse.<String>builder()
                    .success(true)
                    .message("Subscription cancelled successfully")
                    .data("Subscription cancelled")
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Cancel subscription error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<String>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }
}
