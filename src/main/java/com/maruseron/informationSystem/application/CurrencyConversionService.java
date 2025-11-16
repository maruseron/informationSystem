package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.domain.value.HttpResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;

public class CurrencyConversionService {
    /*record Rate(double usd, double eur, String date) {}
    record Variations(change)
    record ExchangeRate(Rate currentRate, Rate previousRate, Variations changePercentage) {}

    static CompletableFuture<BigDecimal> VED_PER_USD_RATE = HttpClient
            .newHttpClient()
            .sendAsync(
                    HttpRequest
                            .newBuilder()
                            .uri(URI.create("https://ve.dolarapi.com/v1/dolares"))
                            .GET()
                            .build(),
                    response -> {

                    });*/
}
