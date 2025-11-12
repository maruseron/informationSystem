package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Payment;
import com.maruseron.informationSystem.domain.enumeration.Currency;

import java.math.BigDecimal;
import java.time.Instant;

public final class PaymentDTO {
    private PaymentDTO() {}

    public static Payment createPayment(Create spec) {
        return new Payment(
                0,
                Instant.now(),
                spec.nid(),
                new BigDecimal(spec.amount()),
                Currency.valueOf(spec.currency()));
    }

    public static Read fromPayment(Payment payment) {
        return new Read(
                payment.getId(),
                payment.getCreatedAt().toEpochMilli(),
                payment.getNid(),
                payment.getAmount().toString(),
                payment.getCurrency().toString());
    }

    public record Create(String nid, String amount, String currency)
            implements DtoTypes.CreateDto<Payment> {}

    public record Read(int id, long createdAt, String nid, String amount, String currency)
            implements DtoTypes.ReadDto<Payment> {}
}
