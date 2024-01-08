package com.exercice.parking.app.controller;

import com.exercice.parking.app.dto.ParkingDTO;
import com.exercice.parking.app.model.DisponibilityParking;
import com.exercice.parking.app.model.Parking;
import com.exercice.parking.app.service.ParkingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@WebMvcTest(ParkingController.class)
public class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingService parkingService;

    @Test
    public void testGetAllParking() throws Exception {
        // Setup
        List<Parking> parkingList = Arrays.asList(new Parking(), new Parking());
        when(parkingService.getListParking()).thenReturn(parkingList);

        // Perform and Verify
        mockMvc.perform(get("/api/v1/parking/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetAllDispoParkingData() throws Exception {
        // Setup
        List<DisponibilityParking> disponibilityParkingList = Arrays.asList(new DisponibilityParking(), new DisponibilityParking());
        when(parkingService.getDispoParking()).thenReturn(disponibilityParkingList);

        // Perform and Verify
        mockMvc.perform(get("/api/v1/parking/disponibility"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetParkingWithDisponibilityPagination() throws Exception {
        // Setup
        List<ParkingDTO> parkingDTOList = Arrays.asList(new ParkingDTO(), new ParkingDTO());
        when(parkingService.getParkingWithDisponibility(anyInt(), anyInt())).thenReturn(parkingDTOList);

        // Perform and Verify
        mockMvc.perform(get("/api/v1/parking/pagination"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetParkingWithDisponibility() throws Exception {
        // Setup
        List<ParkingDTO> parkingDTOList = Arrays.asList(new ParkingDTO(), new ParkingDTO());
        when(parkingService.getParkingWithDisponibility()).thenReturn(parkingDTOList);

        // Perform and Verify
        mockMvc.perform(get("/api/v1/parking"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)));
    }
}
