package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.domain.entity.Employee;
import com.maruseron.informationSystem.domain.enumeration.Role;
import com.maruseron.informationSystem.application.dto.EmployeeDTO;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends BaseService<
        Employee,
        EmployeeDTO.Create,
        EmployeeDTO.Read,
        EmployeeDTO.Update,
        EmployeeRepository> {

    public EmployeeService(EmployeeRepository employeeRepository) {
        repository = employeeRepository;
    }

    @Override
    Employee fromDTO(EmployeeDTO.Create spec) {
        return EmployeeDTO.createEmployee(spec);
    }

    @Override
    EmployeeDTO.Read toDTO(Employee entity) {
        return EmployeeDTO.fromEmployee(entity);
    }

    // TODO: password should be hashed, not stored directly
    /**
     * {@inheritDoc}
     */
    @Override
    Either<EmployeeDTO.Create, HttpResult> validateForCreation(EmployeeDTO.Create request) {
        if (repository.existsByNid(request.nid()))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La persona con la identificación provista ya se encuentra registrada."));

        if (repository.existsByUsername(request.username()))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Este nombre de usuario ya existe."));

        return Either.left(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    Either<Employee, HttpResult> validateAndUpdate(final Employee employee,
                                                   final EmployeeDTO.Update request) {

        // if username is changing and new username already exists
        if (!employee.getUsername().equals(request.username())
            && repository.existsByUsername(request.username())) {
                return Either.right(new HttpResult(HttpStatus.CONFLICT,
                        "Este nombre de usuario ya se encuentra en uso."));
        }

        // if we've reached here, it's safe to change overwrite these fields freely
        employee.setUsername(request.username());
        employee.setPassword(request.password());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setRole(Role.valueOf(request.role().toUpperCase()));
        return Either.left(employee);
    }
}
