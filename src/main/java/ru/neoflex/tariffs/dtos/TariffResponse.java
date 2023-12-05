package ru.neoflex.tariffs.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TariffResponse {
    @Schema(
            description = "name",
            name = "name",
            example = "credit card tariff"
    )
    private String name;
    @Schema(
            description = "start date",
            name = "startDate",
            example = "2000-01-01"
    )
    private LocalDate startDate;
    @Schema(
            description = "end date",
            name = "endDate",
            example = "2001-01-01"
    )
    private LocalDate endDate;
    @Schema(
            description = "description",
            name = "description",
            example = "credit card"
    )
    private String description;
    @Schema(
            description = "rate",
            name = "rate",
            example = "12"
    )
    private Double rate;
    @Schema(
            description = "version",
            name = "version",
            example = "1"
    )
    private Integer version;
}
