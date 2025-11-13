package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.AbsenceDTO;
import com.maruseron.informationSystem.domain.entity.Absence;
import com.maruseron.informationSystem.domain.enumeration.PermissionStatus;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.AbsenceRepository;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class AbsenceService implements
        CreateService<Absence, AbsenceDTO.Create, AbsenceDTO.Read, AbsenceRepository>,
        UpdateService<Absence, AbsenceDTO.Update, AbsenceDTO.Read, AbsenceRepository> {

    @Autowired
    AbsenceRepository repository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public AbsenceRepository repository() {
        return repository;
    }

    @Override
    public Absence fromDTO(AbsenceDTO.Create spec) {
        return AbsenceDTO.createAbsence(
                spec,
                // validateForCreation ensures this call is safe
                employeeRepository.findById(spec.requesterId()).orElseThrow());
    }

    @Override
    public AbsenceDTO.Read toDTO(Absence entity) {
        return AbsenceDTO.fromAbsence(entity);
    }

    @Override
    public Either<AbsenceDTO.Create, HttpResult> validateForCreation(AbsenceDTO.Create request) {
        // reason must be provided
        if (request.reason().isBlank())
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Se debe proporcionar una razón para la solicitud."));

        // startTime must not be prior to the current instant
        // the literal check is "if the instant of the provided timestamp is prior to NOW - 1"
        if (Instant.ofEpochMilli(request.startTime())
                   .isBefore(Instant.now().minus(Duration.ofMinutes(1))))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La fecha de la solicitud no es válida."));

        // convert absence end timestamp to server time
        final var date = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(request.startTime())
                       .plus(Duration.ofMinutes(request.duration())),
                ZoneId.systemDefault());
        // ensure absence end time does not go past 17:00
        if (date.getHour() > 17 || (date.getHour() == 17 && date.getMinute() > 0))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La hora de finalización no puede exceder las 17:00."));

        // duration must be in the range [60, 480]
        if (request.duration() < 60 || request.duration() > 480)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La duración de la solicitud debe ser de no menos de 1 hora y no más de 8."));

        // requester must exist
        if (!employeeRepository.existsById(request.requesterId()))
            return Either.right(new HttpResult(HttpStatus.NOT_FOUND));

        return Either.left(request);
    }

    @Override
    public Either<Absence, HttpResult> validateAndUpdate(Absence entity, AbsenceDTO.Update request) {
        // can only update while pending for action
        if (entity.getPermissionStatus() != PermissionStatus.PENDING)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Ya se ha emitido una decisión para este caso."));

        // the updated status must NOT be PENDING
        final var decision = PermissionStatus.valueOf(request.permissionStatus().toUpperCase());
        if (decision == PermissionStatus.PENDING)
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Se debe emitir una decisión"));

        // supervisor MUST exist
        final var supervisor = employeeRepository.findById(request.supervisorId());
        if (supervisor.isEmpty())
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "El emisor de la decisión que se ha provisto no existe."));

        // modify fields
        entity.setPermissionStatus(decision);
        entity.setSupervisor(supervisor.orElseThrow());
        return Either.left(entity);
    }
}