package com.hotel.booking.service;

import com.hotel.booking.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Value("${hotel.notification.email.enabled:true}")
    private boolean emailEnabled;

    @Value("${hotel.notification.email.from-address:}")
    private String fromAddress;

    public void sendReservationConfirmation(Reservation reservation) {
        if (!emailEnabled) {
            log.info("Email notifications disabled, skipping confirmation for reservation {}", reservation.getId());
            return;
        }
        log.info("Sending reservation confirmation email from {} to guest {} for reservation {}",
                fromAddress, reservation.getGuest().getEmail(), reservation.getId());
    }

    public void sendCancellationNotice(Reservation reservation) {
        if (!emailEnabled) {
            return;
        }
        log.info("Sending cancellation notice to guest {} for reservation {}",
                reservation.getGuest().getEmail(), reservation.getId());
    }
}
