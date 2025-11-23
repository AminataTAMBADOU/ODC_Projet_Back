package com.odk.Service.Interface.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.odk.Entity.Activite;
import com.odk.Entity.HistoriqueSupportActivite;
import com.odk.Entity.SupportActivite;
import com.odk.Entity.Utilisateur;
import com.odk.Enum.StatutSupport;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.HistoriqueSupportActiviteRepository;
import com.odk.Repository.SupportActiviteRepository;
import com.odk.Repository.UtilisateurRepository;
import com.odk.dto.HistoriqueSupportActiviteDTO;
import com.odk.dto.SupportActiviteResponseDTO;

@Service
public class SupportActiviteService {

    private final String uploadDir = "uploads/supports";

    @Autowired
    private SupportActiviteRepository supportActiviteRepository;

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private HistoriqueSupportActiviteRepository historiqueRepository;

    // ---------------- Upload d’un support ------------------
    public SupportActivite saveSupport(MultipartFile file, Long idActivite, String description2, Long utilisateurId, String description) throws IOException {
        // Créer le dossier si inexistant
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Nom unique du fichier
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Récupérer l’activité
        Activite activite = activiteRepository.findById(idActivite)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));

        // Récupérer l'utilisateur affecté
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Créer l’objet support
        SupportActivite support = new SupportActivite();
        support.setNom(file.getOriginalFilename());
        support.setType(file.getContentType());
        support.setUrl("http://localhost:8080/files/" + fileName);
        support.setStatut(StatutSupport.En_ATTENTE);
        support.setActivite(activite);
        support.setUtilisateurAutorise(utilisateur);  // l'utilisateur qui peut modifier/commenter
        support.setDateAjout(new Date());
        support.setDescription(description);

        SupportActivite saved = supportActiviteRepository.save(support);

        // Ajouter l’historique initial
        HistoriqueSupportActivite historique = new HistoriqueSupportActivite();
        historique.setSupport(saved);
        historique.setStatut(saved.getStatut());
        historique.setCommentaire(saved.getCommentaire());
        historique.setDateModification(saved.getDateAjout());
        historiqueRepository.save(historique);

        return saved;
    }

    // ---------------- Mise à jour du statut ------------------
    public SupportActivite updateStatut(Long supportId, StatutSupport statut, String commentaire, String username) {
        SupportActivite support = supportActiviteRepository.findById(supportId)
                .orElseThrow(() -> new RuntimeException("Support non trouvé"));

        // Vérifier que l’utilisateur est autorisé
        if (!support.getUtilisateurAutorise().getUsername().equals(username)) {
            throw new AccessDeniedException("Vous n'êtes pas autorisé à modifier ce support");
        }

        support.setStatut(statut);
        support.setCommentaire(commentaire);
        support.setDateAjout(new Date());

        // Historique
        HistoriqueSupportActivite historique = HistoriqueSupportActivite.builder()
                .support(support)
                .statut(statut)
                .commentaire(commentaire)
                .dateModification(new Date())
                .build();
        historiqueRepository.save(historique);

        return supportActiviteRepository.save(support);
    }

    // ---------------- Liste des supports ------------------
    public List<SupportActiviteResponseDTO> getAllSupports() {
        return supportActiviteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- Support par ID ------------------
    public SupportActiviteResponseDTO getSupportById(Long id) {
        SupportActivite support = supportActiviteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Support non trouvé"));
        return convertToDTO(support);
    }

     // --- DELETE Support ---
    public void deleteSupport(Long supportId, String username) throws IOException {
        SupportActivite support = supportActiviteRepository.findById(supportId)
                .orElseThrow(() -> new RuntimeException("Support non trouvé"));

        // Vérification que l'utilisateur connecté est autorisé
        if (!support.getUtilisateurAutorise().getUsername().equals(username)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce support");
        }

        // Suppression du fichier physique
        Path filePath = Paths.get(support.getUrl());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // Suppression de l'entité en base
        supportActiviteRepository.delete(support);
    }
    
    // Méthode pour récupérer l'historique d'un support
    public List<HistoriqueSupportActiviteDTO> getHistorique(Long supportId) {
        List<HistoriqueSupportActivite> historiques = historiqueRepository.findBySupportId(supportId);
        return historiques.stream()
                .map(h -> new HistoriqueSupportActiviteDTO(
                        h.getId(),
                        h.getStatut(),
                        h.getCommentaire(),
                        h.getDateModification(),
                        h.getEmailAuteur()
                ))
                .collect(Collectors.toList());
    }

    // ---------------- Conversion Entité → DTO ------------------
    public SupportActiviteResponseDTO convertToDTO(SupportActivite support) {
        SupportActiviteResponseDTO dto = new SupportActiviteResponseDTO();
        dto.setId(support.getId());
        dto.setNom(support.getNom());
        dto.setType(support.getType());
        dto.setUrl(support.getUrl());
        dto.setStatut(support.getStatut());
        dto.setDescription(support.getDescription());
        dto.setCommentaire(support.getCommentaire());
        dto.setDateAjout(support.getDateAjout());
        dto.setActiviteId(support.getActivite().getId());
        dto.setActiviteNom(support.getActivite().getNom());
        dto.setEmailutilisateurAutorise(support.getUtilisateurAutorise().getEmail());
        return dto;
    }
}