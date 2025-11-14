package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.CreateService;
import com.maruseron.informationSystem.application.dto.DtoTypes;
import com.maruseron.informationSystem.domain.entity.BaseEntity;
import com.maruseron.informationSystem.domain.entity.Detail;
import com.maruseron.informationSystem.persistence.BaseRepository;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

public interface DetailController<
        Master extends BaseEntity,
        T extends BaseEntity & Detail<Master>,
        Create extends DtoTypes.CreateDto<T>,
        Read extends DtoTypes.ReadDto<T>,
        Repository extends BaseRepository<T>,
        Service extends CreateService<T, Create, Read, Repository>> {

    String endpoint();

    Service detailService();

    @GetMapping("/detail/{id}")
    default ResponseEntity<?> getDetail(@PathVariable int id) {
        return Controllers.handleResult(
                detailService().findById(id),
                ResponseEntity::ok);
    }

    @PostMapping("/{id}")
    default ResponseEntity<?> createDetail(@PathVariable int id,
                                           @RequestBody Create request) {
        return Controllers.handleResult(
                detailService().create(request),
                detail -> ResponseEntity.created(
                        new URI("/" + endpoint() + "/detail/" + detail.id())).body(detail));
    }
}
