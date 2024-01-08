package com.exercice.parking.app.service;

import com.exercice.parking.app.dto.ParkingDTO;
import com.exercice.parking.app.exception.DataRetrievalException;
import com.exercice.parking.app.model.ApiResponse;
import com.exercice.parking.app.model.DisponibilityParking;
import com.exercice.parking.app.model.Parking;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kbaati
 * interface de
 */
public interface ParkingService {
    /**
     * Récupère les données à partir d'une API externe avec gestion des erreurs.
     *
     * @param webClient      Le WebClient pour effectuer la requête.
     * @param externalApiUrl L'URL de l'API externe.
     * @param responseType   Le type de réponse attendu.
     * @param <T>            Le type de données à récupérer.
     * @return Une liste des données récupérées.
     * @throws DataRetrievalException En cas d'échec lors de la récupération des données.
     */


    default <T> List<T> fetchData(WebClient webClient, String externalApiUrl, ParameterizedTypeReference<ApiResponse<T>> responseType) {
        String nextUrl = externalApiUrl;
        List<T> resultList = new ArrayList<>();
        try {
            while (nextUrl != null) {
                Mono<ApiResponse<T>> response = webClient.get()
                        .uri(nextUrl)
                        .retrieve()
                        .onStatus(
                                HttpStatus.INTERNAL_SERVER_ERROR::equals,
                                responseError -> responseError.bodyToMono(String.class).map(DataRetrievalException::new))
                        .onStatus(
                                HttpStatus.BAD_REQUEST::equals,
                                responseError -> responseError.bodyToMono(String.class).map(DataRetrievalException::new))
                        .onStatus(
                                HttpStatus.NOT_FOUND::equals,
                                responseError -> responseError.bodyToMono(String.class).map(DataRetrievalException::new))
                        .bodyToMono(responseType);

                ApiResponse<T> apiResponse = response.blockOptional().orElseThrow(() -> new DataRetrievalException("Réponse nulle lors de la récupération des données."));

                if (apiResponse != null && apiResponse.getResults() != null) {
                    List<T> results = apiResponse.getResults();
                  //  results.forEach(System.out::println);
                    resultList.addAll(results);
                } else {
                    throw new DataRetrievalException("Les résultats de la récupération des données sont nuls.");
                }
                nextUrl = apiResponse.getNext();
            }
        } catch (Exception e) {
            throw new DataRetrievalException("Erreur lors de la récupération des données.", e);
        }

        return resultList;
    }

    /**
     * Récupère la liste des parkings à partir de l'API externe.
     *
     * @return Une liste des parkings.
     * @throws DataRetrievalException En cas d'échec lors de la récupération des parkings.
     */

    List<Parking> getListParking();

    /**
     * Récupère la disponibilité des parkings à partir de l'API externe.
     *
     * @return Une liste des disponibilités de parkings.
     * @throws DataRetrievalException En cas d'échec lors de la récupération des données de disponibilité.
     */
    List<DisponibilityParking> getDispoParking();

    /**
     * Récupère la liste des parkings avec leur disponibilité.
     *
     * @return Une liste de DTO représentant la disponibilité des parkings.
     */
    List<ParkingDTO> getParkingWithDisponibility();

    /**
     * Récupère une liste paginée des parkings avec leur disponibilité.
     *
     * @param page La page à récupérer.
     * @param size La taille de la page.
     * @return Une liste paginée de DTO représentant la disponibilité des parkings.
     */
    List<ParkingDTO> getParkingWithDisponibility(int page, int size);

    /**
     * Crée une liste de DTO à partir des parkings et de leurs disponibilités.
     *
     * @param parkings              La liste des parkings.
     * @param disponibilityParkings La liste de la disponibilité des parkings.
     * @return Une liste de DTO représentant la disponibilité des parkings.
     */
    default List<ParkingDTO> createParkingDTOList(List<Parking> parkings, List<DisponibilityParking> disponibilityParkings) {
        Map<String, String> disponibilityMap = disponibilityParkings.stream()
                .collect(Collectors.toMap(DisponibilityParking::getNom, DisponibilityParking::getPlaces));

        return parkings.stream()
                .map(parking -> new ParkingDTO(parking.getNom(), disponibilityMap.getOrDefault(parking.getNom(), "N/A")))
                .collect(Collectors.toList());
    }
}
