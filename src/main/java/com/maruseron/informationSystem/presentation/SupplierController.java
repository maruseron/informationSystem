package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.SupplierService;
import com.maruseron.informationSystem.application.dto.SupplierDTO;
import com.maruseron.informationSystem.domain.entity.Supplier;
import com.maruseron.informationSystem.persistence.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("supplier")
public class SupplierController implements
        CreateController<Supplier, SupplierDTO.Create, SupplierDTO.Read,
                         SupplierRepository, SupplierService>
{
    @Autowired
    SupplierService service;

    @Override
    public String endpoint() {
        return "supplier";
    }

    @Override
    public SupplierService service() {
        return service;
    }
}