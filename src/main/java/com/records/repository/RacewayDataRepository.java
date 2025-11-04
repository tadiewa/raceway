/**
 * @author : tadiewa
 * date: 10/17/2025
 */


package com.records.repository;


import com.records.model.RacewayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RacewayDataRepository extends JpaRepository<RacewayData, Long> {

    List<RacewayData> findByRacewayName(String racewayName);

    List<RacewayData> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT r FROM RacewayData r WHERE r.createdAt >= :startDate AND r.createdAt < :endDate")
    List<RacewayData> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    List<RacewayData> findByRacewayNameAndCreatedAtBetween(String racewayName,
                                                                 LocalDateTime start,
                                                                 LocalDateTime end);
}
