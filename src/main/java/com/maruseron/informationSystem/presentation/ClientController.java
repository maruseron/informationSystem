package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.ClientService;
import com.maruseron.informationSystem.application.dto.ClientDTO;
import com.maruseron.informationSystem.domain.entity.Client;
import com.maruseron.informationSystem.persistence.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client")
public class ClientController implements
        CreateController<Client, ClientDTO.Create, ClientDTO.Read,
                         ClientRepository, ClientService>,
        UpdateController<Client, ClientDTO.Update, ClientDTO.Read,
                         ClientRepository, ClientService>
{
    @Autowired
    ClientService service;

    @Override
    public String endpoint() {
        return "client";
    }

    @Override
    public ClientService service() {
        return service;
    }
}
