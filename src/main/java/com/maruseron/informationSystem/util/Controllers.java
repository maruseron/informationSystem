package com.maruseron.informationSystem.util;

import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import org.springframework.http.ResponseEntity;
import org.springframework.util.function.ThrowingFunction;

public final class Controllers {
    private Controllers() {}

    public static <T> ResponseEntity<?> handleResult(
            Either<T, HttpResult> result,
            ThrowingFunction<T, ResponseEntity<?>> onLeft) {
        return switch (result) {
            case Either.Left(var employee) ->
                    onLeft.apply(employee);
            case Either.Right(HttpResult(var status, var message)) ->
                    ResponseEntity.status(status).body(message);
        };
    }
}
