package com.odk.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odk.Service.Interface.Service.StatsFichierService;
import com.odk.dto.StatsFichierDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/stats")
public class StatsFichierController {

//---------------------------Injection des dépendences-------------------------//
//----------------------------------------------------------------------------//
    private final StatsFichierService statsService;

//---------------------------Constructeur de la classe-------------------------//
//----------------------------------------------------------------------------//
    public StatsFichierController(StatsFichierService statsService) {
        this.statsService = statsService;
    }

//---------------------------Recuperer la taille des fichiers----------------//
//--------------------------------------------------------------------------//
    @GetMapping("/fichiers")
    @PreAuthorize("hasAnyRole('PERSONNEL', 'SUPERADMIN')")
    public ResponseEntity<StatsFichierDTO> getStatsFichier(){
        StatsFichierDTO stats = statsService.calculerStats();
        return ResponseEntity.ok(stats);
      }
    }
    

