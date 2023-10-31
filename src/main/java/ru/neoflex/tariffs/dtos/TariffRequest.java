package ru.neoflex.tariffs.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TariffRequest {
    @Pattern(regexp = "^([A-Za-z]{2,50})$", message = "Name must contain from 2 to 50 Latin letters")
    @Schema(
            description = "name",
            name = "name",
            example = "credit card tariff"
    )
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date must not be null")
    @Schema(
            description = "start date",
            name = "startDate",
            example = "2000-01-01"
    )
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date must not be null")
    @Schema(
            description = "end date",
            name = "endDate",
            example = "2001-01-01"
    )
    private LocalDate endDate;
    @Pattern(regexp = "^([A-Za-z]{10,100})$", message = "Description must contain from 10 to 100 Latin letters")
    @Schema(
            description = "description",
            name = "description",
            example = "credit card"
    )
    private String description;
    @NotNull(message = "Rate must not be null")
    @Schema(
            description = "rate",
            name = "rate",
            example = "12"
    )
    private Double rate;
}
