package com.exercice.parking.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author  Kbaati
 */
@Data
@NoArgsConstructor
public class Parking implements Serializable {

    private String id ;

    private String nom;

}