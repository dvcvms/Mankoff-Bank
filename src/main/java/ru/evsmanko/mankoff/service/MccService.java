package ru.evsmanko.mankoff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.evsmanko.mankoff.converter.MccConverter;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.repository.MccRepository;

import java.util.List;

@Service
public class MccService {
    @Autowired
    private MccConverter mccConverter;

    @Autowired
    private MccRepository mccRepo;

    public List<MccDto> mccEntities() {
        return mccRepo.findAll()
                .stream()
                .map(u -> mccConverter.convertToDto(u))
                .toList();
    }

    public MccDto save(MccDto mcc) {
        return mccConverter.convertToDto(mccRepo.save(mccConverter.convertToEntity(mcc)));
    }
}
