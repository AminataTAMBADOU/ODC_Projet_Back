package com.odk.Service.Interface.Service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.odk.Entity.SupportActivite;
import com.odk.Repository.SupportActiviteRepository;
import com.odk.dto.StatsFichierDTO;

@Service
public class StatsFichierService {

//-------------------------Injection des dépendences---------------------------------//

    private final SupportActiviteRepository supportActivteRepository;

//-------------------Constructeur de la classe service------------------------------//
//---------------------------------------------------------------------------------//
    public StatsFichierService(SupportActiviteRepository supportActiviteRepository) {
        this.supportActivteRepository=supportActiviteRepository;
    }

//--------------------Le Calcul des statistiques -----------------------------------//
//--------------------------------------------------------------------------------//
    public StatsFichierDTO calculerStats (){
        List<SupportActivite> supports=supportActivteRepository.findAll();

        long tailleTotale=0;
        long taillePdfDoc=0;
        long tailleMedia=0;

        for(SupportActivite support: supports){
            // Securiser taille null -> 0
            Long tailleObj= support.getTaille();
            long taille = (tailleObj != null)? tailleObj:0;

            tailleTotale+=taille;

            //type peut aussi etre null -> sécurité
            String type=(support.getType()!=null)
                        ? support.getType().toLowerCase()
                        :"";

            //Catégorisation par type 
            if(type.contains("pdf")||type.contains("word")||type.contains("doc")||type.contains("xlxs") ||
               type.contains("xls") || type.contains("ppt")|| type.contains("pptx")){

                taillePdfDoc+=taille;

            } 
            else if (type.contains("image")||type.contains("video") || type.contains("png") || type.contains("jpg") ||
                     type.contains("jpeg") || type.contains("mp4") || type.contains("video") || type.contains("mov")||
                      type.contains("avi")) {
                
                        tailleMedia+=taille;
            }
        }
        return new StatsFichierDTO(tailleTotale, taillePdfDoc, tailleMedia);    
    }
}
