package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Devolution;
import com.maruseron.informationSystem.persistence.DevolutionRepository;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("devolution")
public class DevolutionController {
    private final EmployeeRepository employeeRepository;
    private final DevolutionRepository devolutionRepository;

    public DevolutionController(final EmployeeRepository employeeRepository,
                                final DevolutionRepository devolutionRepository) {
        this.employeeRepository = employeeRepository;
        this.devolutionRepository = devolutionRepository;
    }

    @GetMapping
    public ResponseEntity<List<Devolution>> get() {
        return ResponseEntity.ok(
                devolutionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devolution> get(@PathVariable Integer id) {
        if (!devolutionRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                devolutionRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Devolution> create(@RequestBody Devolution request)
            throws URISyntaxException {
        // we extract the employee id from the request body and look for an
        // employee of same id in the database - once extracted, set this
        // non-detached object as the request's employee object, then save
        final var employee = employeeRepository
                .findById(request.getEmployee().getId())
                .orElseThrow(RuntimeException::new);
        request.setEmployee(employee);
        final var devolution = devolutionRepository.save(request);

        return ResponseEntity.created(
                new URI("/devolution/" + devolution.getId())).body(devolution);
    }
}
