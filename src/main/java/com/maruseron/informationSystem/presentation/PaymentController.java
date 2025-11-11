package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.domain.entity.Payment;
import com.maruseron.informationSystem.persistence.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("payment")
public class PaymentController {
    private final PaymentRepository paymentRepository;

    public PaymentController(final PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<Payment> create(@RequestBody Payment request)
            throws URISyntaxException {
        final var payment = paymentRepository.save(request);

        return ResponseEntity.created(
                new URI("/payment/" + payment.getId())).body(payment);
    }
}
