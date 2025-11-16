package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.PaymentDTO;
import com.maruseron.informationSystem.application.dto.SaleDTO;
import com.maruseron.informationSystem.application.dto.TransactionItemDTO;
import com.maruseron.informationSystem.domain.entity.Product;
import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.domain.entity.Sale;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService
        implements CreateService<Sale, SaleDTO.Create, SaleDTO.Read, SaleRepository>
{
    @Autowired
    SaleRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public SaleRepository repository() {
        return repository;
    }

    @Override
    public Sale fromDTO(SaleDTO.Create spec) {
        // items and payments are sent null because they are not yet saved to the database
        // the sale must be saved first, then transaction items and payments must be updated
        // to point to the sale
        return SaleDTO.createSale(
                spec,
                // validateForCreation ensures this call is safe
                employeeRepository.findById(spec.employeeId()).orElseThrow(),
                null,
                null,
                // validateForCreation ensures this call is safe
                clientRepository.findById(spec.clientId()).orElseThrow());
    }

    @Override
    public SaleDTO.Read toDTO(Sale entity) {
        return SaleDTO.fromSale(entity);
    }

    @Override
    public Either<SaleDTO.Create, HttpResult> validateForCreation(SaleDTO.Create request) {
        if (!employeeRepository.existsById(request.employeeId()))
            return Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "El empleado indicado no existe."));

        if (!clientRepository.existsById(request.clientId()))
            return Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "El cliente indicado no existe."));

        if (request.items().isEmpty())
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La lista de artículos está vacía."));

        if (request.payments().isEmpty())
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La lista de pagos está vacía."));

        final var allProductDetailsValid = request
                .items()
                .stream()
                .map(TransactionItemDTO.Create::productDetailId)
                .allMatch(productDetailRepository::existsById);
        if (!allProductDetailsValid)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Uno o más de los productos indicados no existen."));

        // TODO: debería validar esto? no parece ser el caso
        // notas: diferencia de divisas, no es responsabilidad del sist
        final var totalToPay = request
                .items()
                .stream()
                .map(TransactionItemDTO.Create::productDetailId)
                .map(productDetailRepository::findById)
                .map(Optional::orElseThrow)
                .map(ProductDetail::getProduct)
                .map(Product::getSellingPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Either.left(request);
    }
}
