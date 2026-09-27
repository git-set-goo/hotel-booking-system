package com.hotel.booking.service;

import com.hotel.booking.model.Guest;
import com.hotel.booking.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GuestService {

    private static final int POINTS_PER_RESERVATION = 10;

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest getGuestById(Long guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new NoSuchElementException("Guest not found: " + guestId));
    }

    public Guest createGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public void awardLoyaltyPoints(Long guestId) {
        Guest guest = getGuestById(guestId);
        guest.setLoyaltyPoints(guest.getLoyaltyPoints() + POINTS_PER_RESERVATION);
        guestRepository.save(guest);
    }
}
