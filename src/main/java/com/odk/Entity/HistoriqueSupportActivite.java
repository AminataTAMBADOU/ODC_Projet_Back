package com.odk.Entity;


import java.util.Date;

import com.odk.Enum.StatutSupport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class HistoriqueSupportActivite {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "support_id")
    private SupportActivite support;

    @Enumerated(EnumType.STRING)
    private StatutSupport statut;

    @Column(length = 1000)
    private String commentaire;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModification;

    private String emailAuteur;

   
}
