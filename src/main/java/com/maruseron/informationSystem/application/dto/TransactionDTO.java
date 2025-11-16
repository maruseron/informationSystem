package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Transaction;

import java.util.List;

public final class TransactionDTO {
    private TransactionDTO() {}

    public static TransactionDTO.Read fromTransaction(Transaction transaction) {
        return new TransactionDTO.Read(
                transaction.getId(),
                transaction.getCreatedAt().toEpochMilli(),
                EmployeeDTO.fromEmployee(transaction.getEmployee()),
                transaction.getItems()
                           .stream()
                           .map(TransactionItemDTO::fromTransactionItem)
                           .toList());
    }

    public record Read(int id, long createdAt, EmployeeDTO.Read employee,
                       List<TransactionItemDTO.Read> list)
            implements DtoTypes.ReadDto<Transaction> {}
}
