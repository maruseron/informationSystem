package com.maruseron.informationSystem.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Service
public class CurrencyConversionService {
    record ExchangeRate(String fuente, String nombre, double compra, double venta,
                        double promedio, String fechaActualizacion) {}

    static CompletableFuture<BigDecimal> VED_PER_USD_RATE = HttpClient
            .newHttpClient()
            .sendAsync(
                    HttpRequest
                            .newBuilder()
                            .uri(URI.create("https://ve.dolarapi.com/v1/dolares"))
                            .GET()
                            .build(),
                    HttpResponse
                            .BodyHandlers
                            .ofString())
            .thenApply(response -> {
                try {
                    return new ObjectMapper().readValue(response.body(), ExchangeRate.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            })
            .thenApply(ExchangeRate::promedio)
            .thenApply(BigDecimal::new);

    public BigDecimal usdToVed(BigDecimal usd) {
        return usd.multiply(VED_PER_USD_RATE.join());
    }

    public BigDecimal vedToUsd(BigDecimal ved) {
        return ved.divide(VED_PER_USD_RATE.join(), 2, RoundingMode.HALF_UP);
    }
}
