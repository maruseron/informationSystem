package com.maruseron.informationSystem.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface BaseRepository<T> extends JpaRepository<T, Integer> {
    Stream<T> streamAll();
}
