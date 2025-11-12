package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.BrandService;
import com.maruseron.informationSystem.application.CreateService;
import com.maruseron.informationSystem.application.dto.BrandDTO;
import com.maruseron.informationSystem.domain.entity.Brand;
import com.maruseron.informationSystem.persistence.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController implements
    CreateController<Brand, BrandDTO.Create, BrandDTO.Read,
                     BrandRepository, BrandService>
{
    @Autowired
    BrandService service;

    @Override
    public String endpoint() {
        return "brand";
    }

    @Override
    public BrandService service() {
        return service;
    }
}
