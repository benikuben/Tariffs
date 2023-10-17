package ru.neoflex.tariffs.util.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ru.neoflex.tariffs.dtos.TariffRequest;
import ru.neoflex.tariffs.dtos.TariffResponse;
import ru.neoflex.tariffs.models.Tariff;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TariffMapper {
    Tariff tariffDtoToTariff(TariffRequest tariffRequest);

    TariffResponse tariffToTariffDto(Tariff tariff);
}
