package com.exercice.parking.app.service.impl;

import com.exercice.parking.app.model.ApiResponse;
import com.exercice.parking.app.model.Parking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ParkingServiceImplTest {

    @Mock
    private WebClient.Builder webClientBuilderList;

    @Mock
    private WebClient.Builder webClientBuilderDispo;

    @Mock
    private WebClient webClientListParking;

    @Mock
    private WebClient webClientDisponibiliteParking;

    @InjectMocks
    private ParkingServiceImpl parkingServiceImpl;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        String externalApiUrlListParking = "https://data.grandpoitiers.fr/data-fair/api/v1/datasets/mobilite-parkings-grand-poitiers-donnees-metiers/lines/";
        this.webClientListParking = webClientBuilderList.baseUrl(externalApiUrlListParking).build();

        when(webClientBuilderList.baseUrl(externalApiUrlListParking)).thenReturn(webClientBuilderList);
        when(webClientBuilderList.build()).thenReturn(webClientListParking);

        String externalApiUrlParkingDispo = "https://data.grandpoitiers.fr/data-fair/api/v1/datasets/mobilites-stationnement-des-parkings-en-temps-reel/lines";
        when(webClientBuilderDispo.baseUrl(externalApiUrlParkingDispo)).thenReturn(webClientBuilderDispo);
        when(webClientBuilderDispo.build()).thenReturn(webClientDisponibiliteParking);

        parkingServiceImpl = new ParkingServiceImpl(webClientBuilderList, webClientBuilderDispo);



    }

    @Test
    void testGetListParking() {
        // Arrange
        Parking parking = new Parking();
        ApiResponse<Parking> apiResponse = new ApiResponse<>();
        apiResponse.setResults(Collections.singletonList(parking));

        when(webClientListParking.get()).thenReturn(Mockito.mock(WebClient.RequestHeadersUriSpec.class));
        //when(webClientListParking.get().uri((Function<UriBuilder, URI>) any())).thenReturn(Mockito.mock(WebClient.RequestHeadersSpec.class));
        when(webClientListParking.get().uri((URI) any()).retrieve()).thenReturn(Mockito.mock(WebClient.ResponseSpec.class));
        when(webClientListParking.get().uri((URI) any()).retrieve().bodyToMono((Class<Object>) any())).thenReturn(Mono.just(apiResponse));

        // Act
        List<Parking> result = parkingServiceImpl.getListParking();

        // Assert
        assertEquals(Collections.singletonList(parking), result);
    }

    @Test
    void testGetDispoParking() {

    }

    @Test
    void testGetParkingWithDisponibility() {

    }


}
