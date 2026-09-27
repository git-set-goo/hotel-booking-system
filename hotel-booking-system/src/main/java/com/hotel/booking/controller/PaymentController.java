package com.hotel.booking.controller;

import com.hotel.booking.dto.PaymentRequest;
import com.hotel.booking.model.Payment;
import com.hotel.booking.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment processPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }

    @GetMapping("/reservation/{reservationId}")
    public List<Payment> getPaymentsForReservation(@PathVariable Long reservationId) {
        return paymentService.getPaymentsForReservation(reservationId);
    }
}
