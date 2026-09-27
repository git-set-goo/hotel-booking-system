package com.hotel.booking.controller;

import com.hotel.booking.model.Guest;
import com.hotel.booking.service.GuestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/{guestId}")
    public Guest getGuest(@PathVariable Long guestId) {
        return guestService.getGuestById(guestId);
    }

    @PostMapping
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.createGuest(guest);
    }
}
