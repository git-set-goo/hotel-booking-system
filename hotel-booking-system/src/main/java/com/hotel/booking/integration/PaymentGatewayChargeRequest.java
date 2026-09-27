package com.hotel.booking.integration;

import java.math.BigDecimal;

public class PaymentGatewayChargeRequest {

    private String reservationReference;
    private BigDecimal amount;
    private String currency;
    private String method;

    public PaymentGatewayChargeRequest(String reservationReference, BigDecimal amount, String currency, String method) {
        this.reservationReference = reservationReference;
        this.amount = amount;
        this.currency = currency;
        this.method = method;
    }

    public String getReservationReference() { return reservationReference; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getMethod() { return method; }
}
