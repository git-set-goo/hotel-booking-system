package com.hotel.booking.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull
    private Long reservationId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String method;

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
