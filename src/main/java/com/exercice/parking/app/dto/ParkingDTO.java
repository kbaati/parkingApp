package com.exercice.parking.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kbaati
 */
@Data
@NoArgsConstructor
public class ParkingDTO implements Serializable {

    private String nom;
    private String disponibilite;

    public ParkingDTO(String nom, String disponibilite) {
        this.nom = nom;
        this.disponibilite = disponibilite;
    }

}
