package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.EmployeeService;
import com.maruseron.informationSystem.dto.EmployeeDTO;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import com.maruseron.informationSystem.util.Controllers;
import com.maruseron.informationSystem.util.ResponseEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO.Read>> get() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return Controllers.handleResult(
                employeeService.findById(id),
                ResponseEntity::ok);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeDTO.Create request)
            throws URISyntaxException {
        return Controllers.handleResult(
                employeeService.create(request),
                employee -> ResponseEntity.created(
                                new URI("/employee/" + employee.id())).body(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody EmployeeDTO.Update request) {
        return Controllers.handleResult(
                employeeService.update(id, request),
                _ -> ResponseEntity.noContent().build());
    }

    public record AuthRequest(String username, String password) {}

    @PostMapping("/auth")
    public ResponseEntity<String> credentials(@RequestBody AuthRequest request) {
        var employee = employeeRepository.findByUsername(request.username());

        if (employee.isEmpty() || !employee.get().getPassword().equals(request.password()))
            return ResponseEntities.conflict("Credenciales incorrectas.");

        return ResponseEntity.ok("Credenciales correctas.");
    }
}
