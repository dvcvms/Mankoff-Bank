package ru.evsmanko.mankoff.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.evsmanko.mankoff.entity.PaymentEntity;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {

    List<PaymentEntity> findAll();

    List<PaymentEntity> findPaymentEntitiesByBuyerId(long buyerId);

    PaymentEntity save(PaymentEntity payment);
}
