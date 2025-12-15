// package com.odk.Entity;

// import java.util.Date;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.NoArgsConstructor;

// @Entity
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// public class ArchiveSupportActivite {
   
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id; 

//     private Long supportId; // <-- l'identifieant du fichier dans la table support

//     private String nom; 
//     private String type; 
    
//     private String urlArchivee; // <-- Chemin/Lien du fichier sur le disque...
//     private Long taille; // <-- Le poids du fichier en : Octet, KB, MB...
//     private Date dateArchivage; // <-- Date de sauvegarde dans la table Archives...    
//     private String utilisateurAutorise; // <-- Utilisateur habilité à supprimer/deplacer le fichier...
//     private String commentaire; // <-- si tu veux afficher  "supprimé par..."

// }
