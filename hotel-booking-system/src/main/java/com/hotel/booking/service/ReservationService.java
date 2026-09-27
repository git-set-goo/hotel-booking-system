package com.hotel.booking.service;

import com.hotel.booking.dto.ReservationRequest;
import com.hotel.booking.dto.ReservationResponse;
import com.hotel.booking.model.Guest;
import com.hotel.booking.model.Reservation;
import com.hotel.booking.model.ReservationStatus;
import com.hotel.booking.model.Room;
import com.hotel.booking.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final GuestService guestService;
    private final PricingService pricingService;
    private final NotificationService notificationService;

    public ReservationService(ReservationRepository reservationRepository,
                               RoomService roomService,
                               GuestService guestService,
                               PricingService pricingService,
                               NotificationService notificationService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.guestService = guestService;
        this.pricingService = pricingService;
        this.notificationService = notificationService;
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Room room = roomService.getRoomById(request.getRoomId());
        Guest guest = guestService.getGuestById(request.getGuestId());

        BigDecimal totalAmount = pricingService.calculateTotalAmount(
                room, request.getCheckInDate(), request.getCheckOutDate());

        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setGuest(guest);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setTotalAmount(totalAmount);
        reservation.setCreatedAt(LocalDateTime.now());

        Reservation saved = reservationRepository.save(reservation);

        guestService.awardLoyaltyPoints(guest.getId());
        notificationService.sendReservationConfirmation(saved);

        return toResponse(saved);
    }

    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found: " + reservationId));
        return toResponse(reservation);
    }

    public List<ReservationResponse> getReservationsForGuest(Long guestId) {
        return reservationRepository.findByGuestId(guestId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReservationResponse cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found: " + reservationId));

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation saved = reservationRepository.save(reservation);

        notificationService.sendCancellationNotice(saved);

        return toResponse(saved);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getRoom().getId(),
                reservation.getRoom().getRoomNumber(),
                reservation.getGuest().getId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getStatus(),
                reservation.getTotalAmount()
        );
    }
}
