package com.exercice.parking.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {

    private int total;
    private String next;
    private List<T> results;
}
