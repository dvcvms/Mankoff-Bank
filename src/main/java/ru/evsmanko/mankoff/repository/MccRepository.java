package ru.evsmanko.mankoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evsmanko.mankoff.dto.MccDto;
import ru.evsmanko.mankoff.entity.MCCInfoEntity;

import java.util.List;

public interface MccRepository extends JpaRepository<MCCInfoEntity, Long> {
    List<MCCInfoEntity> findAll();
    MCCInfoEntity getMCCInfoEntityByMccCode(long mccCode);
    MCCInfoEntity save(MCCInfoEntity mcc);
}