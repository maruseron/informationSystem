package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Payment;
import com.maruseron.informationSystem.domain.entity.Sale;
import com.maruseron.informationSystem.domain.entity.Transaction;
import com.maruseron.informationSystem.domain.enumeration.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class PaymentDTO {
    private PaymentDTO() {}

    public static Payment createPayment(Create spec, Sale sale) {
        return new Payment(
                0,
                Instant.now(),
                new BigDecimal(spec.amount()),
                PaymentMethod.valueOf(spec.method()),
                sale);
    }

    public static Read fromPayment(Payment payment) {
        return new Read(
                payment.getId(),
                payment.getCreatedAt().toEpochMilli(),
                payment.getAmount().toString(),
                payment.getPaymentMethod().toString());
    }

    public static List<Create> completeCreateSpecs(List<Create> payments, int saleId) {
        return payments.stream().map(p -> p.withMasterId(saleId)).toList();
    }

    public record Create(int saleId, String amount, String method)
            implements DtoTypes.CreateDto<Payment>, DtoTypes.DetailCreateDto<Transaction, Payment> {

        public Create withMasterId(int id) {
            return new Create(id, amount(), method());
        }
    }

    public record Read(int id, long createdAt, String amount, String method)
            implements DtoTypes.ReadDto<Payment> {}
}
