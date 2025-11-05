package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Devolution;
import com.maruseron.informationSystem.domain.TransactionItem;
import com.maruseron.informationSystem.persistence.*;
import com.maruseron.informationSystem.util.ResponseEntities;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("devolution")
public class DevolutionController {
    private final DevolutionRepository      devolutionRepository;
    private final EmployeeRepository        employeeRepository;
    private final ProductDetailRepository   productDetailRepository;
    private final SaleRepository            saleRepository;
    private final TransactionItemRepository transactionItemRepository;

    public DevolutionController(final DevolutionRepository devolutionRepository,
                                final EmployeeRepository employeeRepository,
                                final ProductDetailRepository productDetailRepository,
                                final SaleRepository saleRepository,
                                final TransactionItemRepository transactionItemRepository) {
        this.devolutionRepository      = devolutionRepository;
        this.employeeRepository        = employeeRepository;
        this.productDetailRepository   = productDetailRepository;
        this.saleRepository            = saleRepository;
        this.transactionItemRepository = transactionItemRepository;
    }

    @GetMapping
    public ResponseEntity<List<Devolution>> get() {
        return ResponseEntity.ok(
                devolutionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolution> get(@PathVariable Integer id) {
        if (!devolutionRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                devolutionRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Devolution request)
            throws URISyntaxException {
        // TODO: preguntar tiempo pa la política de devolución

        // if the transaction item list is empty, fail immediately
        if (request.getItems().isEmpty())
            return ResponseEntities.badRequest(
                    "No se ha suministrado ningún producto para la devolución");

        // ensure the sale reference exists and get a live reference from the
        // database
        final var sale = saleRepository
                .findById(request.getSale().getId());

        // if the sale does not exist, fail immediately
        if (sale.isEmpty())
            return ResponseEntities.conflict(
                    "La venta referenciada no existe.");

        request.setSale(sale.get());

        // ensure the one soliciting a devolution is the original buyer
        if (!request.getClient().equals(sale.get().getClient()))
            return ResponseEntities.conflict(
                    "Una devolución solo puede ser solicitada por el comprador.");

        // we can trust the id is not faulty, since it would be provided by
        // the login automatically
        final var employee = employeeRepository
                .findById(request.getEmployee().getId())
                .orElseThrow();
        request.setEmployee(employee);

        // we go through every item in the request for validation
        for (final var item : request.getItems()) {
            // compares a transaction item to "item"
            final Predicate<TransactionItem> teq = item::sameProductVariation;
            // compares a transaction item quantity to item's
            final Predicate<TransactionItem> leq =
                    it -> item.getQuantity() <= it.getQuantity();
            // ensure sale contains an item matching the current item and
            // that there are enough items in the original sale descriptor to
            // return
            if (sale.get().getItems().stream().noneMatch(teq.and(leq)))
                return ResponseEntities.conflict("""
                        Se ha intentado devolver un producto que no figura en la venta \
                        referenciada, o el producto figura, pero se han intentado devolver \
                        más productos de los comprados originalmente.
                        """);
        }

        // we have to save every new transaction item to the db
        request.setItems(request
                .getItems()
                .stream()
                .map(transactionItemRepository::save)
                .toList());

        // now that every item properly references the database, these
        // changes should be reflected on the db
        for (final var item : request.getItems()) {
            final var productDetail = item.getProductDetail();
            productDetail.setStock(productDetail.getStock() + item.getQuantity());
            productDetailRepository.save(productDetail);
        }

        final var devolution = devolutionRepository.save(request);

        return ResponseEntity.created(
                new URI("/devolution/" + devolution.getId())).body(devolution);
    }
}
