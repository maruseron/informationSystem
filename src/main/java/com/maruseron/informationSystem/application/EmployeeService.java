package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.domain.Employee;
import com.maruseron.informationSystem.domain.Role;
import com.maruseron.informationSystem.dto.EmployeeDTO;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import com.maruseron.informationSystem.util.Either;
import com.maruseron.informationSystem.util.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Either<EmployeeDTO.Read, HttpResult> create(final EmployeeDTO.Create request) {
        if (employeeRepository.existsByNid(request.nid()))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La persona con la identificación provista ya se encuentra registrada."));

        if (employeeRepository.existsByUsername(request.username()))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "Este nombre de usuario ya existe."));

        return Either.left(EmployeeDTO.Read.from(
                        employeeRepository.save(Employee.fromDTO(request))));
    }

    public Either<EmployeeDTO.Read, HttpResult> findById(final int id) {
        return employeeRepository
                .findById(id)
                .map(EmployeeDTO.Read::from)
                .<Either<EmployeeDTO.Read, HttpResult>>map(Either::left)
                .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND, "")));
    }

    @Transactional
    public List<EmployeeDTO.Read> findAll() {
        try (final var employees = employeeRepository.streamAll()) {
            return employees.map(EmployeeDTO.Read::from).toList();
        }
    }

    @Transactional
    public Either<EmployeeDTO.Read, HttpResult> update(final int id,
                                                       final EmployeeDTO.Update request) {
        return employeeRepository
                .findById(id)
                .flatMap(e -> updateFields(e, request))
                .map(employeeRepository::save)
                .map(EmployeeDTO.Read::from)
                .<Either<EmployeeDTO.Read, HttpResult>>map(Either::left)
                .orElseGet(() -> Either.right(new HttpResult(HttpStatus.NOT_FOUND, "")));
    }

    Optional<Employee> updateFields(final Employee employee,
                                    final EmployeeDTO.Update request) {

        // if username is changing and new username already exists
        if (!employee.getUsername().equals(request.username())
            && employeeRepository.existsByUsername(request.username())) {
                return Optional.empty();
        }

        // if we've reached here, it's safe to change overwrite these fields freely
        employee.setUsername(request.username());
        employee.setPassword(request.password());
        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setRole(Role.valueOf(request.role().toUpperCase()));
        return Optional.of(employee);
    }
}
