package com.classplus.greetings.repository;

import com.classplus.greetings.model.SharedGreeting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SharedGreetingRepository extends MongoRepository<SharedGreeting, String> {
    List<SharedGreeting> findByUserId(String userId);
    List<SharedGreeting> findByUserIdOrderBySharedAtDesc(String userId);
}
