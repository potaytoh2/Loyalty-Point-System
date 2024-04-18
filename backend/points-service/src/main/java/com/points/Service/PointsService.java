package com.points.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.points.Entity.Points;
import com.points.Exception.ResourceNotFoundException;
import com.points.Repository.PointsRepository;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointsService {

    @Autowired
    private final PointsRepository pointsRepository;

    /**
     * @param id
     * @return Points
     */
    public Points getPointsById(String id) {
        // System.out.println(id);
        return pointsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Points account with id %s does not exist.", id)));
    }

    /**
     * @param userId
     * @return Iterable<Points>
     */
    // public Iterable<Points> getPointsByUserId(String userId) {
    //     Optional<Iterable<Points>> result =  pointsRepository.findByUserId(formatUUIDFromString(userId.toLowerCase()));
    //     if (result.isPresent()) {
    //         return result.get();
    //     } else throw new ResourceNotFoundException(
    //         String.format("Points accounts with user id %s does not exist.", userId));
    // }
    public Iterable<Points> getPointsByUserId(String userId) {
        return pointsRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Points accounts with user id %s does not exist.", userId)));
    }
    
    /** 
     * @param id
     * @param updatedPoints
     * @return Points
     */
    public Points updatePointsById(String id, int updatedPoints) {
        // System.out.println("service line 50");

        Points points = pointsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Points account with id %s does not exist.", id)));

        points.setBalance(updatedPoints);
        return pointsRepository.save(points);
    }

    public static UUID formatUUIDFromString(String str) {
        return UUID.fromString(str.replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
            "$1-$2-$3-$4-$5"
        ));
    }
    
}