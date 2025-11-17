package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.CurrencyConversionService;
import com.maruseron.informationSystem.util.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("currency")
public class CurrencyConversionController {
    @Autowired
    CurrencyConversionService service;

    @GetMapping("/exchange_rate")
    public ResponseEntity<?> exchangeRate() {
        return ResponseEntity.ok(service.exchangeRate());
    }

    @GetMapping("/ved_to_usd/{amount}")
    public ResponseEntity<?> vedToUsd(@PathVariable String amount) {
        return Controllers.handleResult(
                service.vedToUsd(amount),
                ResponseEntity::ok);
    }

    @GetMapping("/usd_to_ved/{amount}")
    public ResponseEntity<?> usdToVed(@PathVariable String amount) {
        return Controllers.handleResult(
                service.usdToVed(amount),
                ResponseEntity::ok);
    }
}
