package com.hotel.booking.dto;

import com.hotel.booking.model.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationResponse {

    private Long id;
    private Long roomId;
    private String roomNumber;
    private Long guestId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;
    private BigDecimal totalAmount;

    public ReservationResponse(Long id, Long roomId, String roomNumber, Long guestId,
                                LocalDate checkInDate, LocalDate checkOutDate,
                                ReservationStatus status, BigDecimal totalAmount) {
        this.id = id;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.guestId = guestId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public Long getRoomId() { return roomId; }
    public String getRoomNumber() { return roomNumber; }
    public Long getGuestId() { return guestId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public ReservationStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
