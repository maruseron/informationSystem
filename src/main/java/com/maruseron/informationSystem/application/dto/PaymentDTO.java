package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Payment;
import com.maruseron.informationSystem.domain.enumeration.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;

public final class PaymentDTO {
    private PaymentDTO() {}

    public static Payment createPayment(Create spec) {
        return new Payment(
                0,
                Instant.now(),
                new BigDecimal(spec.amount()),
                PaymentMethod.valueOf(spec.method()));
    }

    public static Read fromPayment(Payment payment) {
        return new Read(
                payment.getId(),
                payment.getCreatedAt().toEpochMilli(),
                payment.getAmount().toString(),
                payment.getPaymentMethod().toString());
    }

    public record Create(String amount, String method)
            implements DtoTypes.CreateDto<Payment> {}

    public record Read(int id, long createdAt, String amount, String method)
            implements DtoTypes.ReadDto<Payment> {}
}
