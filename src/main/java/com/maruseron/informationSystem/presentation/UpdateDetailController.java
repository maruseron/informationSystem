package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.UpdateService;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.domain.entity.Detail;
import com.maruseron.informationSystem.persistence.BaseRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateDetailController<
        Master extends BaseEntity,
        T extends BaseEntity & Detail<Master>,
        Update extends DtoTypes.UpdateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>,
        Service extends UpdateService<T, Update, Read, Repository>> {

    String endpoint();

    Service detailService();

    @PutMapping("/detail/{id}")
    default ResponseEntity<?> updateDetail(@PathVariable int id,
                                           @RequestBody Update request) {
        return Controllers.handleResult(
                detailService().update(id, request),
                _ -> ResponseEntity.noContent().build());
    }
}