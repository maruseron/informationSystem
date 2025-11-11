package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.ReadService;
import com.maruseron.informationSystem.application.UpdateService;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.persistence.BaseRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateController<
        T extends BaseEntity,
        Update extends DtoTypes.UpdateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>,
        Service extends UpdateService<T, Update, Read, Repository>>
            extends ReadController<T, Read, Repository, Service> {

    @PutMapping("/{id}")
    default ResponseEntity<?> update(@PathVariable Integer id,
                                     @RequestBody Update request) {
        return Controllers.handleResult(
                service().update(id, request),
                _ -> ResponseEntity.noContent().build());
    }
}
