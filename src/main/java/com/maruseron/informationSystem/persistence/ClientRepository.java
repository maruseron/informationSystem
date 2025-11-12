package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends BaseRepository<Client> {
    boolean existsByNid(final String nid);
}
