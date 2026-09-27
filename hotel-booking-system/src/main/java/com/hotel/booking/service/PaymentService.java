package com.hotel.booking.service;

import com.hotel.booking.dto.PaymentRequest;
import com.hotel.booking.integration.PaymentGatewayChargeRequest;
import com.hotel.booking.integration.PaymentGatewayChargeResponse;
import com.hotel.booking.integration.PaymentGatewayClient;
import com.hotel.booking.model.Payment;
import com.hotel.booking.model.Reservation;
import com.hotel.booking.repository.PaymentRepository;
import com.hotel.booking.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentGatewayClient paymentGatewayClient;

    public PaymentService(PaymentRepository paymentRepository,
                           ReservationRepository reservationRepository,
                           PaymentGatewayClient paymentGatewayClient) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
        this.paymentGatewayClient = paymentGatewayClient;
    }

    public Payment processPayment(PaymentRequest request) {
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new NoSuchElementException("Reservation not found: " + request.getReservationId()));

        PaymentGatewayChargeResponse chargeResponse = paymentGatewayClient.charge(
                new PaymentGatewayChargeRequest(
                        "RES-" + reservation.getId(),
                        request.getAmount(),
                        "USD",
                        request.getMethod()));

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus(chargeResponse.getStatus());
        payment.setTransactionDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public java.util.List<Payment> getPaymentsForReservation(Long reservationId) {
        return paymentRepository.findByReservationId(reservationId);
    }
}
