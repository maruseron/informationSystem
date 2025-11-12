package com.maruseron.informationSystem.application;

import com.maruseron.informationSystem.application.dto.PaymentDTO;
import com.maruseron.informationSystem.domain.entity.Payment;
import com.maruseron.informationSystem.domain.value.Either;
import com.maruseron.informationSystem.domain.value.HttpResult;
import com.maruseron.informationSystem.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements
        CreateService<Payment, PaymentDTO.Create, PaymentDTO.Read, PaymentRepository>
{
    @Autowired
    PaymentRepository repository;

    @Override
    public PaymentRepository repository() {
        return repository;
    }

    @Override
    public Payment fromDTO(PaymentDTO.Create spec) {
        return PaymentDTO.createPayment(spec);
    }

    @Override
    public PaymentDTO.Read toDTO(Payment entity) {
        return PaymentDTO.fromPayment(entity);
    }

    @Override
    public Either<PaymentDTO.Create, HttpResult> validateForCreation(PaymentDTO.Create request) {
        if (repository.existsByNid(request.nid()))
            return Either.right(new HttpResult(
                    HttpStatus.CONFLICT,
                    "El pago con la identificación provista ya se encuentra registrado."));

        return Either.left(request);
    }
}
