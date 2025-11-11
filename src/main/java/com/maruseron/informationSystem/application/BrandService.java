package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.BrandDTO;
import com.maruseron.informationSystem.domain.entity.Brand;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BrandService implements
        CreateService<Brand, BrandDTO.Create, BrandDTO.Read, BrandRepository> {

    @Autowired
    BrandRepository repository;

    @Override
    public BrandRepository repository() {
        return repository;
    }

    @Override
    public Brand fromDTO(BrandDTO.Create spec) {
        return BrandDTO.createBrand(spec);
    }

    @Override
    public BrandDTO.Read toDTO(Brand entity) {
        return BrandDTO.fromBrand(entity);
    }

    @Override
    public Either<BrandDTO.Create, HttpResult> validateForCreation(BrandDTO.Create request) {
        return repository.existsByName(request.name())
                ? Either.right(new HttpResult(
                        HttpStatus.CONFLICT,
                "Ya se ha registrado una marca con este nombre."))
                : Either.left(request);
    }
}
