package com.hotel.booking.service;

import com.hotel.booking.model.Room;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class PricingService {

    public BigDecimal calculateTotalAmount(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        if (nights <= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        return room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
    }
}
