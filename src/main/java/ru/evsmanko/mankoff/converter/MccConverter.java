package ru.evsmanko.mankoff.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;

@Component
public class MccConverter {
    private final ModelMapper modelMapper;

    public MccConverter() {
        modelMapper = new ModelMapper();
    }

    public MccDto convertToDto(MCCInfoEntity mcc) {
        return modelMapper.map(mcc, MccDto.class);
    }

    public MCCInfoEntity convertToEntity(MccDto mcc){
        return modelMapper.map(mcc, MCCInfoEntity.class);
    }
}
