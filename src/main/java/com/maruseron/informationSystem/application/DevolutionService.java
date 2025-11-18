package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.DevolutionDTO;
import com.maruseron.informationSystem.application.dto.TransactionItemDTO;
import com.maruseron.informationSystem.domain.entity.Devolution;
import com.maruseron.informationSystem.domain.entity.ProductDetail;
import com.maruseron.informationSystem.domain.entity.Sale;
import com.maruseron.informationSystem.domain.entity.TransactionItem;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class DevolutionService implements
    CreateService<Devolution, DevolutionDTO.Create, DevolutionDTO.Read, DevolutionRepository>
{
    @Autowired
    DevolutionRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Override
    public DevolutionRepository repository() {
        return repository;
    }

    @Override
    public Devolution fromDTO(DevolutionDTO.Create spec) {
        return DevolutionDTO.createDevolution(
                // validateForCreation ensures these calls are safe
                employeeRepository.findById(spec.employeeId()).orElseThrow(),
                null,
                clientRepository.findById(spec.clientId()).orElseThrow(),
                saleRepository.findById(spec.saleId()).orElseThrow());
    }

    @Override
    public DevolutionDTO.Read toDTO(Devolution entity) {
        return DevolutionDTO.fromDevolution(entity);
    }

    @Override
    public Either<DevolutionDTO.Create, HttpResult> validateForCreation(DevolutionDTO.Create request) {
        if (!employeeRepository.existsById(request.employeeId()))
            return Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "El empleado indicado no existe."));

        if (!clientRepository.existsById(request.clientId()))
            return Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "El cliente indicado no existe."));

        if (!saleRepository.existsById(request.clientId()))
            return Either.right(new HttpResult(
                    HttpStatus.NOT_FOUND,
                    "La venta indicada no existe."));

        final var isSameClient = clientRepository
                .findById(request.clientId())
                .orElseThrow()
                .getId() == request.clientId();

        if (!isSameClient)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "El cliente de la transacción actual no coincide con el de la venta " +
                            "referenciada."));

        if (request.items().isEmpty())
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La lista de artículos está vacía."));

        final var someDetailInvalid = request
                .items()
                .stream()
                .map(TransactionItemDTO.Create::productDetailId)
                .anyMatch(not(productDetailRepository::existsById));

        if (someDetailInvalid)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Uno o más de los productos indicados no existen."));

        final var sale = saleRepository.findById(request.saleId()).orElseThrow();

        final var productDetailsInSale = sale
                .getItems()
                .stream()
                .map(TransactionItem::getProductDetail)
                .map(ProductDetail::getId)
                .collect(Collectors.toUnmodifiableSet());

        final Predicate<TransactionItemDTO.Create> variantInSale =
                item -> productDetailsInSale.contains(item.productDetailId());
        final Predicate<TransactionItemDTO.Create> enoughItems =
                item -> item.quantity() <= getVariantQuantityInSale(sale, item.productDetailId());

        final var allNotInSaleOrNotEnough = request
                .items()
                .stream()
                .anyMatch(not(variantInSale.and(enoughItems)));

        if (allNotInSaleOrNotEnough)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Uno o más de los productos indicados no se encuentran en la venta, o se " +
                            "están intentando devolver más productos de los originalmente " +
                            "comprados."));

        return Either.left(request);
    }

    static int getVariantQuantityInSale(Sale sale, int id) {
        return sale
                .getItems()
                .stream()
                .filter(item -> id == item.getProductDetail().getId())
                .findFirst()
                .orElseThrow()
                .getQuantity();
    }
}
