package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.ClientDTO;
import com.maruseron.informationSystem.domain.entity.Client;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements
    CreateService<Client, ClientDTO.Create, ClientDTO.Read, ClientRepository>,
    UpdateService<Client, ClientDTO.Update, ClientDTO.Read, ClientRepository>
{
    @Autowired
    ClientRepository repository;

    @Override
    public ClientRepository repository() {
        return repository;
    }

    @Override
    public Client fromDTO(ClientDTO.Create spec) {
        return ClientDTO.createClient(spec);
    }

    @Override
    public ClientDTO.Read toDTO(Client entity) {
        return ClientDTO.fromClient(entity);
    }

    @Override
    public Either<ClientDTO.Create, HttpResult> validateForCreation(ClientDTO.Create request) {
        if (repository.existsByNid(request.nid()))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "La persona con la identificación provista ya se encuentra registrada."));

        return Either.left(request);
    }

    @Override
    public Either<Client, HttpResult> validateAndUpdate(Client entity, ClientDTO.Update request) {
        entity.setFullName(request.fullName());
        entity.setAddress(request.address());
        return Either.left(entity);
    }
}
