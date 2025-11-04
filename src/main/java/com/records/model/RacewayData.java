/**
 * @author : tadiewa
 * date: 10/17/2025
 */


package com.records.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "raceway_data")
public class RacewayData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @NotNull(message = "Raceway name is required")
    @Column(name = "raceway_name", nullable = false)
    private String racewayName;


    @DecimalMin(value = "0.0", message = "Temperature must be positive")
    @Column(name = "temperature")
    private Double temperature;

    @JsonProperty("pH")
    @NotNull(message = "pH is required")
    @DecimalMin(value = "0.0", message = "pH must be positive")
    @DecimalMax(value = "14.0", message = "pH cannot exceed 14")
    @Column(name = "ph")
    private Double pH;

    @Column(name = "electrical_conductivity")
    private Double electricalConductivity;

    @Column(name = "tds_lower")
    private Integer tdsLower;

    @Column(name = "tds_upper")
    private Integer tdsUpper;

    @Column(name = "salinity")
    private Double salinity;

    @Column(name = "ph_alert")
    private String pHAlert = "OK";

    @Column(name = "tds_alert")
    private String tdsAlert = "OK";

    @Column(name = "salinity_alert")
    private String salinityAlert = "OK";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}



