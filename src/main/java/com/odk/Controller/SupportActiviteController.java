package com.odk.Controller;

import com.odk.Entity.SupportActivite;
import com.odk.Enum.StatutSupport;
import com.odk.Service.Interface.Service.SupportActiviteService;
import com.odk.dto.SupportActiviteResponseDTO;
import com.odk.dto.HistoriqueSupportActiviteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/supports")
public class SupportActiviteController {

    private final SupportActiviteService supportService;

    public SupportActiviteController(SupportActiviteService supportService) {
        this.supportService = supportService;
    }

    // ------------------- UPLOAD -------------------
    @PostMapping("/upload")
    public ResponseEntity<SupportActiviteResponseDTO> uploadSupport(
            @RequestParam("file") MultipartFile file,
            @RequestParam("idActivite") Long idActivite,
            @RequestParam("description") String description,
            @RequestParam("utilisateurId") Long utilisateurId
    ) throws IOException {
        // Récupérer l'utilisateur qui upload / est affecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SupportActivite support = supportService.saveSupport(file, idActivite, description, utilisateurId, username);
        return ResponseEntity.ok(supportService.convertToDTO(support));
    }

    // ------------------- GET ALL -------------------
    @GetMapping
    public ResponseEntity<List<SupportActiviteResponseDTO>> getAllSupports() {
        List<SupportActiviteResponseDTO> supports = supportService.getAllSupports();
        return ResponseEntity.ok(supports);
    }

    // ------------------- GET BY ID -------------------
    @GetMapping("/{id}")
    public ResponseEntity<SupportActiviteResponseDTO> getSupportById(@PathVariable Long id) {
        SupportActiviteResponseDTO dto = supportService.getSupportById(id);
        return ResponseEntity.ok(dto);
    }

    // ------------------- UPDATE STATUT -------------------
    @PatchMapping("/{id}")
    public ResponseEntity<SupportActiviteResponseDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam("statut") StatutSupport statut,
            @RequestParam(value = "commentaire", required = false) String commentaire
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SupportActivite updated = supportService.updateStatut(id, statut, commentaire, username);
        return ResponseEntity.ok(supportService.convertToDTO(updated));
    }

    // ------------------- DELETE -------------------
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupport(@PathVariable Long id) throws IOException {
    // Récupération de l'utilisateur connecté via le JWT
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

    // Appel du service avec l'id du support et le username
        supportService.deleteSupport(id, username);

    return ResponseEntity.noContent().build();
}
//-----------------------------------------------------------
@GetMapping("/{id}/historique")
public ResponseEntity<List<HistoriqueSupportActiviteDTO>> getHistorique(@PathVariable Long id) {
    List<HistoriqueSupportActiviteDTO> historiques = supportService.getHistorique(id);
    return ResponseEntity.ok(historiques);
}
   
}