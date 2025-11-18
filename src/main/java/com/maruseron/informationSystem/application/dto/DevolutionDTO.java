package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.*;

import java.time.Instant;
import java.util.List;

public final class DevolutionDTO {
    private DevolutionDTO() {}

    public static Devolution createDevolution(Employee employee, List<TransactionItem> items,
                                              Client client, Sale sale) {
        return new Devolution(
                0,
                Instant.now(),
                employee,
                items,
                client,
                sale);
    }

    public static Read fromDevolution(Devolution entity) {
        return new Read(
                entity.getId(),
                entity.getCreatedAt().toEpochMilli(),
                EmployeeDTO.fromEmployee(entity.getEmployee()),
                entity.getItems() == null ? null :
                        entity.getItems().stream().map(TransactionItemDTO::fromTransactionItem).toList(),
                ClientDTO.fromClient(entity.getClient()),
                SaleDTO.fromSale(entity.getSale()));
    }

    public record Create(int employeeId, List<TransactionItemDTO.Create> items, int clientId,
                         int saleId)
            implements DtoTypes.CreateDto<Devolution> {}

    public record Read(int id, long createdAt, EmployeeDTO.Read employee,
                       List<TransactionItemDTO.Read> items, ClientDTO.Read client,
                       SaleDTO.Read sale)
            implements DtoTypes.ReadDto<Devolution> {}
}
