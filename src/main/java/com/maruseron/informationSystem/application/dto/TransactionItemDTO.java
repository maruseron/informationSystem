package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.domain.entity.Transaction;
import com.maruseron.informationSystem.domain.entity.TransactionItem;
import com.maruseron.informationSystem.domain.enumeration.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public final class TransactionItemDTO {
    private TransactionItemDTO() {}

    public static TransactionItem createTransactionItem(Create spec,
                                                        Transaction transaction,
                                                        ProductDetail productDetail,
                                                        BigDecimal amount) {
        return new TransactionItem(
                0,
                Instant.now(),
                transaction,
                productDetail,
                amount,
                spec.quantity());
    }

    public static Read fromTransactionItem(TransactionItem transactionItem) {
        return new Read(
                transactionItem.getId(),
                transactionItem.getCreatedAt().toEpochMilli(),
                ProductDetailDTO.fromProductDetail(transactionItem.getProductDetail()),
                transactionItem.getAmount().toString(),
                transactionItem.getQuantity());
    }

    public static List<Create> completeCreateSpecs(List<Create> specs, int masterId,
                                                   TransactionType transactionType) {
        return specs
                .stream()
                .map(item -> item.withMasterIdAndTransactionType(masterId, transactionType))
                .toList();
    }

    public record Create(int transactionId, int productDetailId, int quantity,
                         TransactionType transactionType)
            implements DtoTypes.DetailCreateDto<Transaction, TransactionItem> {

        public Create withMasterId(int id) {
            return new Create(id, productDetailId(), quantity(), transactionType());
        }

        public Create withMasterIdAndTransactionType(int id, TransactionType transactionType) {
            return new Create(id, productDetailId(), quantity(), transactionType);
        }
    }

    public record Read(int id, long createdAt, ProductDetailDTO.Read productDetail,
                       String amount, int quantity)
            implements DtoTypes.ReadDto<TransactionItem> {}
}
