package ru.evsmanko.mankoff.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.InjectionStrategy;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MccMapper {
    MccDto toDto(MCCInfoEntity entity);

    MCCInfoEntity toEntity(MccDto dto);
}
