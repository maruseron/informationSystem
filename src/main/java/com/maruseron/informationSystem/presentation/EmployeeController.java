package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.EmployeeService;
import com.maruseron.informationSystem.application.dto.EmployeeDTO;
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
}
