package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.Employee;
import com.maruseron.informationSystem.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Employee> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getUser(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity<Employee> createUser(@RequestBody Employee request)
            throws URISyntaxException {
        final var user = userRepository.save(request);
        return ResponseEntity.created(
                new URI("/user/" + user.getId())).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateUser(@PathVariable Integer id,
                                               @RequestBody Employee request) {
        var user =
                userRepository.findById(id).orElseThrow(RuntimeException::new);
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setNid(request.getNid());
        user.setRole(request.getRole());
        user = userRepository.save(request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().body(id.toString());
    }
}
