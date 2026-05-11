package com.classplus.greetings.service;

import com.classplus.greetings.dto.TemplateDTO;
import com.classplus.greetings.model.Template;
import com.classplus.greetings.repository.TemplateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public List<TemplateDTO> getAllTemplates() {
        List<Template> templates = templateRepository.findByIsActive(true);
        return templates.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TemplateDTO> getTemplatesByCategory(String category) {
        List<Template> templates = templateRepository.findByCategory(category);
        return templates.stream()
                .filter(Template::isActive)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TemplateDTO> getPremiumTemplates() {
        List<Template> templates = templateRepository.findByIsPremiumAndIsActive(true, true);
        return templates.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TemplateDTO> getFreeTemplates() {
        List<Template> templates = templateRepository.findByIsPremiumAndIsActive(false, true);
        return templates.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TemplateDTO getTemplateById(String id) {
        Optional<Template> templateOptional = templateRepository.findByIdAndIsActive(id, true);
        if (templateOptional.isEmpty()) {
            throw new RuntimeException("Template not found");
        }
        return mapToDTO(templateOptional.get());
    }

    public TemplateDTO createTemplate(TemplateDTO request) {
        Template template = new Template();
        template.setTitle(request.getTitle());
        template.setCategory(request.getCategory());
        template.setImageUrl(request.getImageUrl());
        template.setImageThumbnailUrl(request.getImageThumbnailUrl());
        template.setIsPremium(request.isPremium());
        template.setDescription(request.getDescription());
        template.setActive(true);
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());

        Template savedTemplate = templateRepository.save(template);
        log.info("Template created: {}", savedTemplate.getId());

        return mapToDTO(savedTemplate);
    }

    public void incrementLikes(String templateId) {
        Optional<Template> templateOptional = templateRepository.findById(templateId);
        if (templateOptional.isPresent()) {
            Template template = templateOptional.get();
            template.setLikes(template.getLikes() + 1);
            templateRepository.save(template);
        }
    }

    public void incrementShares(String templateId) {
        Optional<Template> templateOptional = templateRepository.findById(templateId);
        if (templateOptional.isPresent()) {
            Template template = templateOptional.get();
            template.setShares(template.getShares() + 1);
            templateRepository.save(template);
        }
    }

    private TemplateDTO mapToDTO(Template template) {
        return TemplateDTO.builder()
                .id(template.getId())
                .title(template.getTitle())
                .category(template.getCategory())
                .imageUrl(template.getImageUrl())
                .imageThumbnailUrl(template.getImageThumbnailUrl())
                .isPremium(template.isPremium())
                .description(template.getDescription())
                .likes(template.getLikes())
                .shares(template.getShares())
                .createdAt(template.getCreatedAt())
                .build();
    }
}
