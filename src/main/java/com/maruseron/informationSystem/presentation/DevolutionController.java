package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.*;
import com.maruseron.informationSystem.application.dto.DevolutionDTO;
import com.maruseron.informationSystem.application.dto.PaymentDTO;
import com.maruseron.informationSystem.application.dto.SaleDTO;
import com.maruseron.informationSystem.application.dto.TransactionItemDTO;
import com.maruseron.informationSystem.domain.entity.Devolution;
import com.maruseron.informationSystem.domain.entity.TransactionItem;
import com.maruseron.informationSystem.domain.enumeration.TransactionType;
import com.maruseron.informationSystem.persistence.*;
import com.maruseron.informationSystem.util.Controllers;
import com.maruseron.informationSystem.util.ResponseEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("devolution")
public class DevolutionController implements
        CreateController<Devolution, DevolutionDTO.Create, DevolutionDTO.Read,
                         DevolutionRepository, DevolutionService>
{
    @Autowired
    DevolutionService service;

    @Autowired
    TransactionItemService transactionItemService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ProductDetailService productDetailService;

    @Override
    public String endpoint() {
        return "devolution";
    }

    @Override
    public DevolutionService service() {
        return service;
    }

    @Override
    public ResponseEntity<?> create(DevolutionDTO.Create request) throws URISyntaxException {
        return Controllers.handleResult(
                service().create(request).flatMap(read -> {
                    transactionItemService.bulkCreate(
                            TransactionItemDTO.completeCreateSpecs(
                                    request.items(),
                                    read.id(),
                                    TransactionType.DEVOLUTION));
                    productDetailService.increaseStockFor(request.items());
                    return service.findById(read.id());
                }),
                read -> ResponseEntity.created(
                        new URI("/" + endpoint() + "/" + read.id())).body(read));
    }
}
