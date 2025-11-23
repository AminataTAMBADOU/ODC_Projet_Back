package com.odk.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SupportActiviteRequestDTO {
    private MultipartFile file;
    private Long activiteId;
    private Long utilisateurId; // utilisateur affecté
    private String type;
    private String description;
}