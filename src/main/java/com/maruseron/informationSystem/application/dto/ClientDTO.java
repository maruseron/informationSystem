package com.maruseron.informationSystem.application.dto;

import com.maruseron.informationSystem.domain.entity.Client;

import java.time.Instant;

public final class ClientDTO {
    private ClientDTO() {}

    public static Client createClient(Create spec) {
        return new Client(
                0,
                Instant.now(),
                spec.fullName(),
                spec.nid(),
                spec.address());
    }

    public static Read fromClient(Client client) {
        return new Read(
                client.getId(),
                client.getCreatedAt().toEpochMilli(),
                client.getFullName(),
                client.getNid(),
                client.getAddress());
    }

    public record Create(String fullName, String nid, String address)
            implements DtoTypes.CreateDto<Client> {}

    public record Read(int id, long createdAt, String fullName, String nid, String address)
            implements DtoTypes.ReadDto<Client> {}

    public record Update(String fullName, String address)
            implements DtoTypes.UpdateDto<Client> {}
}
