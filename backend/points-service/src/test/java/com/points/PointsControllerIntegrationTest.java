package com.points;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.points.Entity.Points;
import com.points.Repository.PointsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PointsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PointsRepository pointsRepository;

    private String userId;
    private String pointsId;

    @BeforeEach
    void setup() {
        // Clean up the repository and add a test record before each test
        pointsRepository.deleteAll();
        userId = UUID.randomUUID().toString();
        Points points = new Points();
        points.setUserId(userId);
        points.setBalance(1000);
        Points savedPoints = pointsRepository.save(points);
        pointsId = savedPoints.getId();
    }

    @Test
    void getAllPoints_ShouldReturnPointsList() throws Exception {
        mockMvc.perform(get("/points")
                .param("userId", userId))
                .andExpect(status().isOk());
    }

    @Test
    void getPoints_ShouldReturnPoints() throws Exception {
        mockMvc.perform(get("/points/{id}", pointsId))
                .andExpect(status().isOk());
    }

    @Test
    void updatePoints_ShouldUpdatePoints() throws Exception {
        String requestBody = "2000";

        mockMvc.perform(put("/points/{id}", pointsId)
                .contentType(MediaType.TEXT_PLAIN) 
                .content(requestBody))
                .andExpect(status().isAccepted());
    
        Points updatedPoints = pointsRepository.findById(pointsId).orElse(null);
        assertNotNull(updatedPoints);
        assertEquals(2000, updatedPoints.getBalance());
    }
    
}
