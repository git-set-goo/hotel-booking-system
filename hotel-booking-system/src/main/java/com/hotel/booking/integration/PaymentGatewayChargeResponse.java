package com.hotel.booking.integration;

public class PaymentGatewayChargeResponse {

    private String transactionId;
    private String status;

    public PaymentGatewayChargeResponse() {
    }

    public PaymentGatewayChargeResponse(String transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
