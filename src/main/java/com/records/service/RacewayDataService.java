/**
 * @author : tadiewa
 * date: 10/17/2025
 */


package com.records.service;




import com.records.exception.RacewayDataNotFoundException;
import com.records.exception.RacewayDataValidationException;
import com.records.model.RacewayData;
import com.records.repository.RacewayDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class RacewayDataService {

    @Autowired
    private RacewayDataRepository repository;

    public List<RacewayData> findAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RacewayDataNotFoundException("Error retrieving raceway data", e);
        }
    }

    public RacewayData findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RacewayDataNotFoundException(id));
    }

    @Transactional
    public RacewayData save(RacewayData racewayData) {
        try {
            racewayData.setCreatedAt(LocalDateTime.now());
            racewayData.setUpdatedAt(LocalDateTime.now());
            log.info("Saving raceway data: ---------------------------------------->{}", racewayData);
            validateRacewayData(racewayData);
            if (racewayData.getId() != null && racewayData.getId() == 0) {
                racewayData.setId(null);
            }
            return repository.save(racewayData);
        } catch (Exception e) {
            throw new RacewayDataValidationException("Error saving raceway data: " + e.getMessage());
        }
    }

    public RacewayData update(Long id, RacewayData racewayData) {
        try {
            if (!repository.existsById(id)) {
                throw new RacewayDataNotFoundException(id);
            }
            validateRacewayData(racewayData);
            racewayData.setId(id);
            return repository.save(racewayData);
        } catch (RacewayDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RacewayDataValidationException("Error updating raceway data: " + e.getMessage());
        }
    }

    public void deleteById(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new RacewayDataNotFoundException(id);
            }
            repository.deleteById(id);
        } catch (RacewayDataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RacewayDataValidationException("Error deleting raceway data: " + e.getMessage());
        }
    }

    public List<RacewayData> findByRacewayName(String racewayName) {
        try {
            return repository.findByRacewayName(racewayName);
        } catch (Exception e) {
            throw new RacewayDataNotFoundException("Error finding data for raceway: " + racewayName, e);
        }
    }

    public List<RacewayData> findByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.plusDays(1).atStartOfDay();
            return repository.findByDateRange(start, end);
        } catch (Exception e) {
            throw new RacewayDataNotFoundException("Error finding data for date range", e);
        }
    }

    private void validateRacewayData(RacewayData data) {
        if (data.getRacewayName() == null || data.getRacewayName().trim().isEmpty()) {
            throw new RacewayDataValidationException("Raceway name is required");
        }
        if (data.getCreatedAt() == null) {
            throw new RacewayDataValidationException("Measurement date is required");
        }
        if (data.getPH() != null && (data.getPH() < 0 || data.getPH() > 14)) {
            throw new RacewayDataValidationException("pH must be between 0 and 14");
        }
    }
}
