package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.*;

import java.time.Instant;
import java.util.List;

public final class SaleDTO {
    private SaleDTO() {}

    public static Sale createSale(Create spec, Employee employee, List<TransactionItem> items,
                                  List<Payment> payments, Client client) {
        return new Sale(
                0,
                Instant.now(),
                employee,
                items,
                payments,
                client);
    }

    public static Read fromSale(Sale entity) {
        return new Read(
                entity.getId(),
                entity.getCreatedAt().toEpochMilli(),
                EmployeeDTO.fromEmployee(entity.getEmployee()),
                null,
                null,
                ClientDTO.fromClient(entity.getClient()));
    }

    public record Create(int employeeId, List<TransactionItemDTO.Create> items,
                         List<PaymentDTO.Create> payments, int clientId)
            implements DtoTypes.CreateDto<Sale> {}

    public record Read(int id, long createdAt, EmployeeDTO.Read employee,
                       List<TransactionItemDTO.Read> items, List<PaymentDTO.Read> payments,
                       ClientDTO.Read client)
            implements DtoTypes.ReadDto<Sale> {}
}
