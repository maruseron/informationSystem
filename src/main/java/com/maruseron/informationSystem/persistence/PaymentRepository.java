package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {}
