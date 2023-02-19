package ru.evsmanko.mankoff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.evsmanko.mankoff.entity.UserEntity;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAll();
    UserEntity getUserEntityById(long id);
    UserEntity save(UserEntity user);
}
