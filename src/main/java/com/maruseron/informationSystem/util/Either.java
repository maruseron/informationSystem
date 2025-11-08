package com.maruseron.informationSystem.util;

public sealed interface Either<L, R>
        permits Either.Left, Either.Right {

    record Left<L, R>(L value) implements Either<L, R> {}
    record Right<L, R>(R error) implements Either<L, R> {}

    static <L, R> Left<L, R> left(L value) {
        return new Left<>(value);
    }

    static <L, R> Right<L, R> right(R value) {
        return new Right<>(value);
    }
}
