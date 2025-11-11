package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.ReadService;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.persistence.BaseRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ReadController<
        T extends BaseEntity,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>,
        Service extends ReadService<T, Read, Repository>> {

    Service service();

    @GetMapping
    default ResponseEntity<List<Read>> get() {
        return ResponseEntity.ok(service().findAll());
    }

    @GetMapping("/{id}")
    default ResponseEntity<?> get(@PathVariable Integer id) {
        return Controllers.handleResult(
                service().findById(id),
                ResponseEntity::ok);
    }
}
