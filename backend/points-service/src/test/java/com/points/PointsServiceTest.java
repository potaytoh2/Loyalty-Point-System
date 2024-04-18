package com.points;

import com.points.Repository.*;
import com.points.Service.*;
import com.points.Entity.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import optional
import java.util.Arrays;
import java.util.Optional;
import com.points.Exception.*;

@ExtendWith(MockitoExtension.class)
public class PointsServiceTest {
    
    @Mock
    private PointsRepository pointsRepository;

    @InjectMocks
    private PointsService pointsService;

    private static Points mockPoints;
    private static Points mockPoints2;
    private static String id;
    private static String userId;
    private static String id2;

    @BeforeAll
    static void setUp() {

        // Two points accounts belonging to the same user
        mockPoints = new Points();
        mockPoints2 = new Points();
        id = UUID.randomUUID().toString();
        id2 = UUID.randomUUID().toString();
        userId = UUID.randomUUID().toString();
        mockPoints.setId(id);
        mockPoints.setUserId(userId);
        mockPoints.setBalance(1500);
        mockPoints2.setId(id2);
        mockPoints2.setUserId(userId);
        mockPoints2.setBalance(9000);
    }

    @Test
    void getPointsById_existingPoints_returnsPoints() {
        //Arrange
        mockPoints.setId(id);
        when(pointsRepository.findById(id)).thenReturn(Optional.of(mockPoints));
        
        // Act
        Points foundPoints = pointsService.getPointsById(id);
        
        // Assert
        assertNotNull(foundPoints);
        assertEquals(id, foundPoints.getId());
    }

    @Test
    void getUserById_nonExistingUser_throwsException() {
        // Arrange
        when(pointsRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> pointsService.getPointsById(id));
    }

    @Test
    void getPointsByUserId_returnsPointsList() {
        // Arrange
        when(pointsRepository.findByUserId(userId)).thenReturn(Optional.of(Arrays.asList(mockPoints, mockPoints2)));
        
        // Act
        Iterable<Points> points = pointsService.getPointsByUserId(userId);
        
        // Assert
        assertNotNull(points);
        assertTrue(points.iterator().hasNext());
    }

    @Test
    void updatePoints_updatesPoints() {
        // Arrange
        Points existingPoints = new Points();
        existingPoints.setBalance(1000);
        existingPoints.setId(id);
        existingPoints.setUserId(userId);
        when(pointsRepository.save(any(Points.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(pointsRepository.findById(id)).thenReturn(Optional.of(existingPoints));
        
        // Act
        Points updatedPoints = pointsService.updatePointsById(id, 10000);
        
        // Assert
        assertEquals(10000, updatedPoints.getBalance());
    }
    
    @Test
    void updatePoints_pointsDoesNotExist() {
        // Arrange
        when(pointsRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> { pointsService.updatePointsById(id, 10000);});   
    }
}
