package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.entity.Attendance;
import com.maruseron.informationSystem.persistence.AttendanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("attendance")
public class AttendanceController {
    private final AttendanceRepository attendanceRepository;

    public AttendanceController(final AttendanceRepository absenceRepository) {
        this.attendanceRepository = absenceRepository;
    }

    @GetMapping
    public ResponseEntity<List<Attendance>> get() {
        return ResponseEntity.ok(
                attendanceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> get(@PathVariable Integer id) {
        if (!attendanceRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(
                attendanceRepository.findById(id)
                        .orElseThrow(RuntimeException::new));
    }

    @PostMapping
    public ResponseEntity<Attendance> create(@RequestBody Attendance request)
            throws URISyntaxException {
        final var attendance = attendanceRepository.save(request);

        return ResponseEntity.created(
                new URI("/attendance/" + attendance.getId())).body(attendance);
    }
}