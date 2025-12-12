package com.odk.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Participant{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String genre;
    private Date createdAt = new Date();
    private boolean checkedIn = false;  // Champ pour indiquer si le participant a été vérifié
    private LocalDateTime checkInTime;  // Champ pour l'heure de check-in

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "activite_id")
    @JsonBackReference
    private Activite activite;

    @ManyToOne
    @JoinColumn(name = "liste_id")
    @JsonIgnore
    private Liste liste;
    // Ajoutez un constructeur prenant un ID
    public Participant(Long id) {
        this.id = id;
    }

}
