# Hotel Booking System

A sample Spring Boot application for hotel room reservations, guests, and payments.

## Modules

- `controller` – REST APIs (Room, Guest, Reservation, Payment, Availability)
- `service` – Business logic (ReservationService, RoomService, GuestService, PaymentService, PricingService, NotificationService)
- `integration` – Third-party Payment Gateway client (`PaymentGatewayClient`) used by `PaymentService` to authorize charges before a `Payment` is persisted
- `repository` – Spring Data JPA repositories
- `model` – JPA entities
- `dto` – Request/response models

## APIs

- `GET /api/rooms/available?checkInDate=&checkOutDate=`
- `GET /api/rooms/{roomId}`
- `GET /api/guests/{guestId}` / `POST /api/guests`
- `POST /api/reservations` / `GET /api/reservations/{reservationId}` / `GET /api/reservations/guest/{guestId}` / `POST /api/reservations/{reservationId}/cancel`
- `POST /api/payments` / `GET /api/payments/reservation/{reservationId}`
- `GET /api/availability/quote?roomId=&checkInDate=&checkOutDate=`

## Database

H2 in-memory database, schema managed via Flyway (`src/main/resources/db/migration/V1__init.sql`).
Tables: `rooms`, `guests`, `reservations`, `payments`.

## Configuration

See `src/main/resources/application.yml` for reservation limits, payment gateway settings, and notification settings.
