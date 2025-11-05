package com.maruseron.informationSystem.util;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public final class Validators {
    private Validators() {}

    /**
     * Checks if a given key is **unique** across all entities managed by the specified
     * {@link JpaRepository}, based on a provided selector function.
     * <p>
     * This method iterates over all entities retrieved from the repository and uses the
     * {@code selector} function to extract a comparable key from each entity. It then
     * checks if **none** of these extracted keys are equal to the provided **target key**.
     *
     * @param <T> The type of the entity managed by the {@code JpaRepository}.
     * @param <K> The type of the key to check for uniqueness, which must be comparable
     * using the {@code equals()} method.
     * @param source The {@code JpaRepository} to query for all entities.
     * @param key The target key of type {@code K} that is being checked for uniqueness
     * against the keys extracted from the entities.
     * @param selector A **function** that accepts an entity of type {@code T} and
     * returns the key of type {@code K} to be compared.
     * @return **{@code true}** if the {@code key} is unique (i.e., no entity in the
     * repository yields a matching key via the selector); **{@code false}** otherwise.
     * @implSpec The current implementation retrieves *all* entities from the repository
     * into memory using {@code source.findAll()} and then streams through them
     * to perform the uniqueness check. For large datasets, this may be
     * inefficient and is **not recommended for production environments** with
     * many records. A better approach for performance would be a custom query
     * in the repository (e.g., a **{@code countBy...}** method).
     */
    public static <T, K> boolean ensureUnique(JpaRepository<T, ?> source, K key,
                                       Function<T, K> selector) {
        return source.findAll().stream().noneMatch(item -> key.equals(selector.apply(item)));
    }
}
