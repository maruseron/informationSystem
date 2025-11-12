package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.AbsenceService;
import com.maruseron.informationSystem.domain.entity.Absence;
import com.maruseron.informationSystem.application.dto.AbsenceDTO;
import com.maruseron.informationSystem.persistence.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("absence")
public class AbsenceController implements
        CreateController<Absence, AbsenceDTO.Create, AbsenceDTO.Read,
                         AbsenceRepository, AbsenceService>,
        UpdateController<Absence, AbsenceDTO.Update, AbsenceDTO.Read,
                         AbsenceRepository, AbsenceService>
{
    @Autowired
    AbsenceService service;

    @Override
    public String endpoint() {
        return "absence";
    }

    @Override
    public AbsenceService service() {
        return service;
    }
}