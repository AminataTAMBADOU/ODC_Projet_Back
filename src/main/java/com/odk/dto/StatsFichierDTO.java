package com.odk.dto;

import org.aspectj.apache.bcel.generic.RET;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatsFichierDTO {
    private long tailleTotale;  // <-- Taille totale de tous les fichiers en bytes ...
    private long taillePdfDoc; //  <-- Taille totale des PDF/ WORD / DOCUMENTS ...
    private long tailleMedia; //   <-- Taille totale des images / videos ...

    // Méthodes pour retourner les tailles lisibles ...
       public String getTailleTotaleFormatee(){
                return formatSize(tailleTotale);
       }

       public String getTaillePdfDocFormatee(){
                return formatSize(taillePdfDoc);
       }

       public String getTailleMediaFormatee(){
                return formatSize(tailleMedia);
       }


       private String formatSize(long size) {
        if(size<1024) return size + " B";
        int exp = (int) (Math.log(size)/Math.log(1024));
        char unit="KMGTPE".charAt(exp -1);
        return String.format("%.2f %sB", size/Math.pow(1024, exp), unit);
       }
}
