
package com.odk.dto;

import java.util.Date;

import com.odk.Enum.StatutSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriqueSupportActiviteDTO {
    private Long id;
    private StatutSupport statut;
    private String commentaire;
    private Date dateModification;
    private String emailAuteur;
}