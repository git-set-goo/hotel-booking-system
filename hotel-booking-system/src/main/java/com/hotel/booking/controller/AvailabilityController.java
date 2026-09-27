package com.hotel.booking.controller;

import com.hotel.booking.model.Room;
import com.hotel.booking.service.PricingService;
import com.hotel.booking.service.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final RoomService roomService;
    private final PricingService pricingService;

    public AvailabilityController(RoomService roomService, PricingService pricingService) {
        this.roomService = roomService;
        this.pricingService = pricingService;
    }

    @GetMapping("/quote")
    public BigDecimal getPriceQuote(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        Room room = roomService.getRoomById(roomId);
        return pricingService.calculateTotalAmount(room, checkInDate, checkOutDate);
    }
}
