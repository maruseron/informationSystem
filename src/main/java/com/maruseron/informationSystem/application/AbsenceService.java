package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.AbsenceDTO;
import com.maruseron.informationSystem.domain.entity.Absence;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.AbsenceRepository;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AbsenceService extends BaseService<
        Absence,
        AbsenceDTO.Create,
        AbsenceDTO.Read,
        AbsenceDTO.Update,
        AbsenceRepository> {

    @Autowired
    EmployeeRepository employeeRepository;

    public AbsenceService(AbsenceRepository absenceRepository) {
        this.repository = absenceRepository;
    }

    @Override
    Absence fromDTO(AbsenceDTO.Create spec) {
        return AbsenceDTO.createAbsence(
                spec,
                // validateForCreation ensures this call is safe
                employeeRepository.findById(spec.employeeId()).orElseThrow());
    }

    @Override
    AbsenceDTO.Read toDTO(Absence entity) {
        return AbsenceDTO.fromAbsence(entity);
    }

    @Override
    Either<AbsenceDTO.Create, HttpResult> validateForCreation(AbsenceDTO.Create request) {
        return employeeRepository.existsById(request.employeeId())
                ? Either.left(request)
                : Either.right(new HttpResult(HttpStatus.NOT_FOUND));
    }

    // TODO: think about absence validation
    @Override
    Either<Absence, HttpResult> validateAndUpdate(Absence entity, AbsenceDTO.Update spec) {
        return null;
    }
}
