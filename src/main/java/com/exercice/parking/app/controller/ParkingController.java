package com.exercice.parking.app.controller;

import com.exercice.parking.app.dto.ParkingDTO;
import com.exercice.parking.app.model.DisponibilityParking;
import com.exercice.parking.app.model.Parking;
import com.exercice.parking.app.service.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Kbaati
 * notre controlleur
 */
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    //TODO ici je travail directement avec le model (Parking), il fallait passer par un DTO mais just pour gain de temps...

    /**
     * Récupérer la liste des parkings .
     *
     * @return une liste de Parking si trouvé
     */
    @GetMapping("/list")
    public List<Parking> getAllParking() {
        LOGGER.debug("REST request to get parking liste");
        return parkingService.getListParking();
    }
    //TODO ici je travail directement avec le model, il fallait passer par un DTO mais just pour gain de temps...

    /**
     * Récupérer la liste des disponibilité.
     *
     * @return une liste de disponibilité de Parking
     */
    @GetMapping("/disponibility")
    public List<DisponibilityParking> getAllDispoParkingData() {
        LOGGER.debug("REST request to get parking dispo");
        return parkingService.getDispoParking();
    }



    /**
     * Récupérer les informations de stationnement.
     *
     * @param page
     * @param pageSize
     * @return une liste  d'information de Parking avec les nombre de place dispo avec pagination
     */
    @GetMapping("pagination")
    public List<ParkingDTO> getParkingWithDisponibilityPagination(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "5") int pageSize) {
        LOGGER.debug("REST request to get parking with dispo with pagination");
        return parkingService.getParkingWithDisponibility(page, pageSize);
    }

    /**
     * Récupérer les informations de stationnement .
     *
     * @return une liste  d'information de Parking avec les nombre de place dispo
     */
    @GetMapping("")
    public List<ParkingDTO> getParkingWithDisponibility() {
        LOGGER.debug("REST request to get parking dispo");
        return parkingService.getParkingWithDisponibility();
    }


}