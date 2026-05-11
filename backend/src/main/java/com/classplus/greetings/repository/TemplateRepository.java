package com.classplus.greetings.repository;

import com.classplus.greetings.model.Template;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {
    List<Template> findByCategory(String category);
    List<Template> findByIsPremiumAndIsActive(boolean isPremium, boolean isActive);
    List<Template> findByIsActive(boolean isActive);
    Optional<Template> findByIdAndIsActive(String id, boolean isActive);
}
