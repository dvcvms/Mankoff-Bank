package ru.evsmanko.mankoff.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.evsmanko.mankoff.converter.MccConverter;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;
import ru.evsmanko.mankoff.mapper.MccMapper;
import ru.evsmanko.mankoff.repository.MccRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MccService {

    private MccConverter mccConverter;
    private final MccMapper mapper;

    @Autowired
    private MccRepository mccRepo;

    public List<MccDto> mccEntities() {
        return mccRepo.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<MccDto> save(MccDto dto) {
        Optional<MCCInfoEntity> mccEntity = mccRepo.save(mapper.toEntity(dto));

        if (mccEntity.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(mapper.toDto(mccEntity.get()));
    }
}
