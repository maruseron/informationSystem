package com.maruseron.informationSystem.persistence;

import java.util.stream.Stream;

public interface StreamableRepository<T> {
    Stream<T> streamAllBy();
}
