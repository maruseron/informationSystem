package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.SupplierDTO;
import com.maruseron.informationSystem.domain.entity.Supplier;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SupplierService implements
    CreateService<Supplier, SupplierDTO.Create, SupplierDTO.Read, SupplierRepository>
{
    @Autowired
    SupplierRepository repository;

    @Override
    public SupplierRepository repository() {
        return repository;
    }

    @Override
    public SupplierDTO.Read toDTO(Supplier entity) {
        return SupplierDTO.fromSupplier(entity);
    }

    @Override
    public Supplier fromDTO(SupplierDTO.Create spec) {
        return SupplierDTO.createSupplier(spec);
    }

    @Override
    public Either<SupplierDTO.Create, HttpResult> validateForCreation(SupplierDTO.Create request) {
        return repository.existsByNid(request.nid())
                ? Either.right(new HttpResult(
                        HttpStatus.CONFLICT,
                        "El proveedor con la identificación provista ya se encuentra registrado."))
                : Either.left(request);
    }
}
