package com.exercice.parking.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kbaati
 */
@Data
@NoArgsConstructor
public class DisponibilityParking implements Serializable {

    @JsonProperty("Nom")
    private String nom;

    @JsonProperty("Places")
    private String places;

    @JsonProperty("Capacite")
    private String capacite;

}