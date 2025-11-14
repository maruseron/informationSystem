package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.EmployeeService;
import com.maruseron.informationSystem.application.dto.EmployeeDTO;
import com.maruseron.informationSystem.domain.entity.Employee;
import com.maruseron.informationSystem.persistence.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
public class EmployeeController implements
        CreateController<Employee, EmployeeDTO.Create, EmployeeDTO.Read,
                         EmployeeRepository, EmployeeService>,
        UpdateController<Employee, EmployeeDTO.Update, EmployeeDTO.Read,
                         EmployeeRepository, EmployeeService>
{
    @Autowired
    EmployeeService service;

    @Override
    public String endpoint() {
        return "employee";
    }

    @Override
    public EmployeeService service() {
        return service;
    }
}
