package com.hotel.booking.service;

import com.hotel.booking.dto.ReservationRequest;
import com.hotel.booking.dto.ReservationResponse;
import com.hotel.booking.model.Guest;
import com.hotel.booking.model.Reservation;
import com.hotel.booking.model.ReservationStatus;
import com.hotel.booking.model.Room;
import com.hotel.booking.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock private ReservationRepository reservationRepository;
    @Mock private RoomService roomService;
    @Mock private GuestService guestService;
    @Mock private PricingService pricingService;
    @Mock private NotificationService notificationService;

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationService(
                reservationRepository, roomService, guestService, pricingService, notificationService);
    }

    @Test
    void createReservation_calculatesTotalAndSendsConfirmation() {
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        room.setPricePerNight(BigDecimal.valueOf(100));

        Guest guest = new Guest();
        guest.setId(2L);
        guest.setEmail("guest@example.com");

        ReservationRequest request = new ReservationRequest();
        request.setRoomId(1L);
        request.setGuestId(2L);
        request.setCheckInDate(LocalDate.now().plusDays(1));
        request.setCheckOutDate(LocalDate.now().plusDays(3));

        when(roomService.getRoomById(1L)).thenReturn(room);
        when(guestService.getGuestById(2L)).thenReturn(guest);
        when(pricingService.calculateTotalAmount(any(), any(), any())).thenReturn(BigDecimal.valueOf(200));

        Reservation saved = new Reservation();
        saved.setId(10L);
        saved.setRoom(room);
        saved.setGuest(guest);
        saved.setCheckInDate(request.getCheckInDate());
        saved.setCheckOutDate(request.getCheckOutDate());
        saved.setStatus(ReservationStatus.CONFIRMED);
        saved.setTotalAmount(BigDecimal.valueOf(200));

        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        ReservationResponse response = reservationService.createReservation(request);

        assertEquals(BigDecimal.valueOf(200), response.getTotalAmount());
        verify(guestService).awardLoyaltyPoints(2L);
        verify(notificationService).sendReservationConfirmation(saved);
    }
}
