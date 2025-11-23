package com.odk.Entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.odk.Enum.StatutSupport;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportActivite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nom;
    private String type;
    private String url;

    @Column(length = 100)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAjout;

    @Enumerated(EnumType.STRING)
    private StatutSupport statut=StatutSupport.En_ATTENTE;

    @Column(length = 100)
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "activte_id")
    private Activite activite;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateurAutorise;

    @OneToMany(mappedBy = "support", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoriqueSupportActivite> historiques;

    public void mettreAJourStatut(StatutSupport nouveauStatut, String commentaire) {
            this.statut = nouveauStatut;
            this.commentaire=commentaire;
    }

}
