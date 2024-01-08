package com.exercice.parking.app.service.impl;

import com.exercice.parking.app.dto.ParkingDTO;
import com.exercice.parking.app.model.ApiResponse;
import com.exercice.parking.app.model.DisponibilityParking;
import com.exercice.parking.app.model.Parking;
import com.exercice.parking.app.service.ParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * @author Kbaati
 *  implementation de notre service
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private final WebClient webClientListParking;

    private final WebClient webClientDisponibiliteParking;


    @Value("${external.api.url.list.parking}")
    private String externalApiUrlListParking;

    @Value("${external.api.url.parking.dispo}")
    private String externalApiUrlParkingDispo;


    public ParkingServiceImpl(WebClient.Builder webClientBuilderList, WebClient.Builder webClientBuilderDispo) {
        this.webClientListParking = webClientBuilderList.baseUrl(externalApiUrlListParking).build();
        this.webClientDisponibiliteParking = webClientBuilderDispo.baseUrl(externalApiUrlParkingDispo).build();

    }

    @Override
    public List<Parking> getListParking() {
        LOGGER.debug("getListParking");
        return fetchData(webClientListParking, externalApiUrlListParking, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public List<DisponibilityParking> getDispoParking() {
        LOGGER.debug("getDispoParking");
        return fetchData(webClientDisponibiliteParking, externalApiUrlParkingDispo, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public List<ParkingDTO> getParkingWithDisponibility() {
        LOGGER.debug("getParkingWithDisponibility");
        List<Parking> parkings = getListParking();
        List<DisponibilityParking> disponibilityParkings = getDispoParking();
        return createParkingDTOList(parkings, disponibilityParkings);
    }

    @Override
    public List<ParkingDTO> getParkingWithDisponibility(int page, int size) {
        LOGGER.debug("getParkingWithDisponibility");
        List<Parking> allParkings = getListParking();
        List<DisponibilityParking> allDisponibilityParkings = getDispoParking();

        // Calculer l'index de début et de fin pour la pagination
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, allParkings.size());

        List<Parking> paginatedParkings = allParkings.subList(startIndex, endIndex);
        return createParkingDTOList(paginatedParkings, allDisponibilityParkings);
    }

}
