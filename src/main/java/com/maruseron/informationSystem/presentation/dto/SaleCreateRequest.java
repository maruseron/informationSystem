package com.maruseron.informationSystem.presentation.dto;

import com.maruseron.informationSystem.presentation.SaleController;

import java.util.List;

public record SaleCreateRequest(List<SaleController.PaymentCreateRequest> payments, int clientId) {
}
