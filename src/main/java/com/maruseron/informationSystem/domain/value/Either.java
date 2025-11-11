package com.maruseron.informationSystem.domain.value;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

public sealed interface Either<L, R>
        permits Either.Left, Either.Right {

    // left maps
    <L1> Either<L1, R> map(Function<L, L1> f);
    <L1> Either<L1, R> flatMap(Function<L, Either<L1, R>> f);

    // right maps
    <R1> Either<L, R1> mapRight(Function<R, R1> f);
    <R1> Either<L, R1> flatMapRight(Function<R, Either<L, R1>> f);

    // if right, throw, else unbox
    L orElseThrow();

    // if left, return self, else call supplier
    Either<L, R> orElseGet(Supplier<Either<L, R>> supplier);

    record Left<L, R>(L value) implements Either<L, R> {
        @Override public <L1> Either<L1, R> map(Function<L, L1> f) {
            return Either.left(f.apply(value));
        }

        @Override public <L1> Either<L1, R> flatMap(Function<L, Either<L1, R>> f) {
            return f.apply(value);
        }

        @SuppressWarnings("unchecked")
        @Override public <R1> Either<L, R1> mapRight(Function<R, R1> f) {
            return (Either<L, R1>) this;
        }

        @SuppressWarnings("unchecked")
        @Override public <R1> Either<L, R1> flatMapRight(Function<R, Either<L, R1>> f) {
            return (Either<L, R1>) this;
        }

        @Override public L orElseThrow() {
            return value;
        }

        @Override public Either<L, R> orElseGet(Supplier<Either<L, R>> supplier) {
            return this;
        }
    }

    record Right<L, R>(R value) implements Either<L, R> {
        @SuppressWarnings("unchecked")
        @Override public <L1> Either<L1, R> map(Function<L, L1> f) {
            return (Either<L1, R>) this;
        }

        @SuppressWarnings("unchecked")
        @Override public <L1> Either<L1, R> flatMap(Function<L, Either<L1, R>> f) {
            return (Either<L1, R>) this;
        }

        @Override public <R1> Either<L, R1> mapRight(Function<R, R1> f) {
            return Either.right(f.apply(value));
        }

        @Override public <R1> Either<L, R1> flatMapRight(Function<R, Either<L, R1>> f) {
            return f.apply(value);
        }

        @Override public L orElseThrow() {
            throw new NoSuchElementException();
        }

        @Override public Either<L, R> orElseGet(Supplier<Either<L, R>> supplier) {
            return supplier.get();
        }
    }

    static <L, R> Left<L, R> left(L value) {
        return new Left<>(value);
    }

    static <L, R> Right<L, R> right(R value) {
        return new Right<>(value);
    }
}
