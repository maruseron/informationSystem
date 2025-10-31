package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Employee;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> get() {
        return ResponseEntity.ok(
                employeeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Integer id) {
        if (!employeeRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                employeeRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee request)
            throws URISyntaxException {
        final var employee = employeeRepository.save(request);

        return ResponseEntity.created(
                new URI("/employee/" + employee.getId())).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id,
                                           @RequestBody Employee request) {
        var employee = employeeRepository.findById(id)
                                         .orElseThrow(RuntimeException::new);

        employee.setUsername(request.getUsername());
        employee.setPassword(request.getPassword());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setNid(request.getNid());
        employee.setRole(request.getRole());

        employeeRepository.save(employee);
        return ResponseEntity.noContent().build();
    }
}
