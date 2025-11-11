package com.maruseron.informationSystem.persistence;

import com.maruseron.informationSystem.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.stream.Stream;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Integer> {
    Stream<T> streamAllBy();
}
