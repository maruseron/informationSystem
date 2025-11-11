package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.BaseService;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.application.dto.EmployeeDTO;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.StreamableRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.function.ThrowingFunction;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public abstract class BaseController<
        T extends BaseEntity,
        Create extends DtoTypes.CreateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Update extends DtoTypes.UpdateDto<T>,
        Repository extends StreamableRepository<T> & JpaRepository<T, Integer>,
        Service extends BaseService<T, Create, Read, Update, Repository>> {

    Service service;
    String endpoint;

    public ResponseEntity<?> handleResult(
            Either<T, HttpResult> result,
            ThrowingFunction<T, ResponseEntity<?>> onLeft) {
        return switch (result) {
            case Either.Left(var employee) ->
                    onLeft.apply(employee);
            case Either.Right(HttpResult(var status, var message)) ->
                    ResponseEntity.status(status).body(message);
        };
    }

    @GetMapping
    public ResponseEntity<List<Read>> get() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return Controllers.handleResult(
                service.findById(id),
                ResponseEntity::ok);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Create request)
            throws URISyntaxException {
        return Controllers.handleResult(
                service.create(request),
                read -> ResponseEntity.created(
                        new URI("/employee/" + read.id())).body(read));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Update request) {
        return Controllers.handleResult(
                service.update(id, request),
                _ -> ResponseEntity.noContent().build());
    }
}
