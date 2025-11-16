package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.CreateService;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.persistence.BaseRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;

public interface CreateController<
        T extends BaseEntity,
        Create extends DtoTypes.CreateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>,
        Service extends CreateService<T, Create, Read, Repository>>
            extends ReadController<T, Read, Repository, Service> {

    String endpoint();

    @PostMapping
    default ResponseEntity<?> create(@RequestBody Create request) throws URISyntaxException {
        return Controllers.handleResult(
                service().create(request),
                read -> ResponseEntity.created(
                        new URI("/" + endpoint() + "/" + read.id())).body(read));
    }
}
