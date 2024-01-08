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


@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParkingController.class);


    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/list")
    public List<Parking> getAllParking() {
        LOGGER.debug("REST request to get parking liste");
        return parkingService.getListParking();
    }

    @GetMapping("/disponibility")
    public List<DisponibilityParking> getAllDispoParkingData() {
        LOGGER.debug("REST request to get parking dispo");
        return parkingService.getDispoParking();
    }


    @GetMapping("pagination")
    public List<ParkingDTO> getParkingWithDisponibilityPagination(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "5") int pageSize) {
        LOGGER.debug("REST request to get parking with dispo with pagination");
        return parkingService.getParkingWithDisponibility(page,pageSize);
    }

    @GetMapping("")
    public List<ParkingDTO> getParkingWithDisponibility() {
        LOGGER.debug("REST request to get parking dispo");
        return parkingService.getParkingWithDisponibility();
    }


}