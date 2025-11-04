/**
 * @author : tadiewa
 * date: 10/17/2025
 */


package com.records.api;

import com.records.model.RacewayData;
import com.records.service.RacewayDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/raceway-data")
@Slf4j
public class RacewayDataController {


    private final RacewayDataService service;

    @GetMapping
    public List<RacewayData> getAllRacewayData() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RacewayData> getRacewayDataById(@PathVariable Long id) {
        RacewayData data = service.findById(id);
        return ResponseEntity.ok(data);
    }

    @PostMapping
    public RacewayData createRacewayData(@Valid @RequestBody RacewayData racewayData) {
        log.info("pH value: {}", racewayData.getPH());
        return service.save(racewayData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RacewayData> updateRacewayData(@PathVariable Long id,
                                                         @Valid @RequestBody RacewayData racewayData) {
        RacewayData updatedData = service.update(id, racewayData);
        return ResponseEntity.ok(updatedData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRacewayData(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/raceway/{name}")
    public List<RacewayData> getRacewayDataByName(@PathVariable String name) {
        return service.findByRacewayName(name);
    }

    @GetMapping("/date-range")
    public List<RacewayData> getRacewayDataByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return service.findByDateRange(startDate, endDate);
    }
}
