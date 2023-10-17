package ru.neoflex.tariffs.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TariffRequest {
    @Pattern(regexp = "^([A-Za-z]{2,50})$", message = "Name must contain from 2 to 50 Latin letters")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date must not be null")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date must not be null")
    private LocalDate endDate;
    @Pattern(regexp = "^([A-Za-z]{10,100})$", message = "Description must contain from 10 to 100 Latin letters")
    private String description;
    @NotNull(message = "Rate must not be null")
    private Double rate;
}
