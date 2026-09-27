package com.hotel.booking.controller;

import com.hotel.booking.dto.ReservationRequest;
import com.hotel.booking.dto.ReservationResponse;
import com.hotel.booking.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationResponse createReservation(@Valid @RequestBody ReservationRequest request) {
        return reservationService.createReservation(request);
    }

    @GetMapping("/{reservationId}")
    public ReservationResponse getReservation(@PathVariable Long reservationId) {
        return reservationService.getReservation(reservationId);
    }

    @GetMapping("/guest/{guestId}")
    public List<ReservationResponse> getReservationsForGuest(@PathVariable Long guestId) {
        return reservationService.getReservationsForGuest(guestId);
    }

    @PostMapping("/{reservationId}/cancel")
    public ReservationResponse cancelReservation(@PathVariable Long reservationId) {
        return reservationService.cancelReservation(reservationId);
    }
}
