package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Absence;
import com.maruseron.informationSystem.domain.entity.Employee;
import com.maruseron.informationSystem.domain.enumeration.PermissionStatus;

import java.time.Instant;

public final class AbsenceDTO {

    private AbsenceDTO() {}

    public static Absence createAbsence(Create spec, Employee requester) {
        return new Absence(
                0,
                Instant.now(),
                spec.reason(),
                PermissionStatus.PENDING,
                Instant.ofEpochSecond(spec.startTime),
                spec.duration(),
                null,
                requester);
    }

    public static Read fromAbsence(Absence absence) {
        final var supervisor = absence.getSupervisor();
        return new AbsenceDTO.Read(
                absence.getId(),
                absence.getReason(),
                absence.getPermissionStatus().toString(),
                absence.getStartTime().toEpochMilli(),
                absence.getDuration(),
                supervisor == null ? null : EmployeeDTO.fromEmployee(supervisor),
                EmployeeDTO.fromEmployee(absence.getRequester()),
                absence.getCreatedAt().toEpochMilli());
    }

    public record Create(String reason, long startTime, int duration, int requesterId)
        implements DtoTypes.CreateDto<Absence> {}

    public record Read(int id, String reason, String permissionStatus, long startTime,
                       int duration, EmployeeDTO.Read supervisor, EmployeeDTO.Read requester,
                       long createdAt)
        implements DtoTypes.ReadDto<Absence> {}

    public record Update(String permissionStatus, int supervisorId)
        implements DtoTypes.UpdateDto<Absence> {}
}
