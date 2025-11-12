package com.maruseron.informationSystem.presentation;

import com.maruseron.informationSystem.application.PaymentService;
import com.maruseron.informationSystem.application.dto.PaymentDTO;
import com.maruseron.informationSystem.domain.entity.Payment;
import com.maruseron.informationSystem.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController implements
        ReadController<Payment, PaymentDTO.Read,
                       PaymentRepository, PaymentService>
{
    @Autowired
    PaymentService service;

    @Override
    public PaymentService service() {
        return service;
    }
}
