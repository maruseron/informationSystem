package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends BaseRepository<Payment> {
    boolean existsByNid(final String nid);
}
