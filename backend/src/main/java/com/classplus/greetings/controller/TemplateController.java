package com.classplus.greetings.controller;

import com.classplus.greetings.dto.ApiResponse;
import com.classplus.greetings.dto.TemplateDTO;
import com.classplus.greetings.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/templates")
@CrossOrigin(origins = "*")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TemplateDTO>>> getAllTemplates() {
        try {
            List<TemplateDTO> templates = templateService.getAllTemplates();
            return ResponseEntity.ok(ApiResponse.<List<TemplateDTO>>builder()
                    .success(true)
                    .message("Templates retrieved successfully")
                    .data(templates)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get templates error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<TemplateDTO>>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<TemplateDTO>>> getTemplatesByCategory(@PathVariable String category) {
        try {
            List<TemplateDTO> templates = templateService.getTemplatesByCategory(category);
            return ResponseEntity.ok(ApiResponse.<List<TemplateDTO>>builder()
                    .success(true)
                    .message("Templates retrieved successfully")
                    .data(templates)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get templates by category error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<TemplateDTO>>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/premium")
    public ResponseEntity<ApiResponse<List<TemplateDTO>>> getPremiumTemplates() {
        try {
            List<TemplateDTO> templates = templateService.getPremiumTemplates();
            return ResponseEntity.ok(ApiResponse.<List<TemplateDTO>>builder()
                    .success(true)
                    .message("Premium templates retrieved successfully")
                    .data(templates)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get premium templates error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<TemplateDTO>>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/free")
    public ResponseEntity<ApiResponse<List<TemplateDTO>>> getFreeTemplates() {
        try {
            List<TemplateDTO> templates = templateService.getFreeTemplates();
            return ResponseEntity.ok(ApiResponse.<List<TemplateDTO>>builder()
                    .success(true)
                    .message("Free templates retrieved successfully")
                    .data(templates)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get free templates error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.<List<TemplateDTO>>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TemplateDTO>> getTemplateById(@PathVariable String id) {
        try {
            TemplateDTO template = templateService.getTemplateById(id);
            return ResponseEntity.ok(ApiResponse.<TemplateDTO>builder()
                    .success(true)
                    .message("Template retrieved successfully")
                    .data(template)
                    .timestamp(System.currentTimeMillis())
                    .build());
        } catch (Exception e) {
            log.error("Get template error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<TemplateDTO>builder()
                            .success(false)
                            .error(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }
}
