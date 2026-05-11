package com.classplus.greetings.service;

import com.classplus.greetings.model.Subscription;
import com.classplus.greetings.model.User;
import com.classplus.greetings.repository.SubscriptionRepository;
import com.classplus.greetings.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    public Subscription createSubscription(String userId, String planType) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();

        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID().toString());
        subscription.setUserId(userId);
        subscription.setPlanType(planType);
        subscription.setCurrency("INR");
        
        if ("MONTHLY".equalsIgnoreCase(planType)) {
            subscription.setAmount(299.0);
            subscription.setStartDate(LocalDateTime.now());
            subscription.setExpiryDate(LocalDateTime.now().plus(30, ChronoUnit.DAYS));
        } else if ("ANNUAL".equalsIgnoreCase(planType)) {
            subscription.setAmount(2499.0);
            subscription.setStartDate(LocalDateTime.now());
            subscription.setExpiryDate(LocalDateTime.now().plus(365, ChronoUnit.DAYS));
        }

        subscription.setStatus("ACTIVE");
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setUpdatedAt(LocalDateTime.now());

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // Update user subscription tier
        user.setSubscriptionTier(User.SubscriptionTier.PREMIUM);
        user.setSubscriptionExpiryDate(savedSubscription.getExpiryDate());
        userRepository.save(user);

        log.info("Subscription created for user: {} with plan: {}", userId, planType);
        return savedSubscription;
    }

    public Subscription getSubscriptionByUserId(String userId) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE");
        return subscriptionOptional.orElse(null);
    }

    public boolean isUserPremium(String userId) {
        Subscription subscription = getSubscriptionByUserId(userId);
        if (subscription == null) {
            return false;
        }
        return subscription.getExpiryDate().isAfter(LocalDateTime.now());
    }

    public void cancelSubscription(String userId) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE");
        if (subscriptionOptional.isPresent()) {
            Subscription subscription = subscriptionOptional.get();
            subscription.setStatus("CANCELLED");
            subscription.setUpdatedAt(LocalDateTime.now());
            subscriptionRepository.save(subscription);

            // Update user subscription tier
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setSubscriptionTier(User.SubscriptionTier.FREE);
                user.setSubscriptionExpiryDate(null);
                userRepository.save(user);
            }

            log.info("Subscription cancelled for user: {}", userId);
        }
    }
}
