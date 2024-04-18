package com.points.Repository;

import org.springframework.data.repository.CrudRepository;
import com.points.Entity.Points;
import java.util.Optional;
import java.util.UUID;


public interface PointsRepository extends CrudRepository<Points, String>{
    
    // Optional<Iterable<Points>> findByUserId(UUID userId);
    Optional<Iterable<Points>> findByUserId(String userId);
}
