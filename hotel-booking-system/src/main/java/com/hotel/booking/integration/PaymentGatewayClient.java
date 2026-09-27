package com.hotel.booking.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Client for the external third-party Payment Gateway service.
 * Charges are submitted synchronously and must succeed before a Payment is persisted locally.
 */
@Component
public class PaymentGatewayClient {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayClient.class);

    private final RestTemplate restTemplate;

    @Value("${hotel.payment.gateway.url}")
    private String gatewayUrl;

    @Value("${hotel.payment.gateway.api-key:}")
    private String apiKey;

    public PaymentGatewayClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PaymentGatewayChargeResponse charge(PaymentGatewayChargeRequest request) {
        try {
            PaymentGatewayChargeResponse response = restTemplate.postForObject(
                    gatewayUrl + "/charges", request, PaymentGatewayChargeResponse.class);
            if (response == null) {
                throw new PaymentGatewayException("Payment gateway returned an empty response");
            }
            return response;
        } catch (RestClientException ex) {
            log.error("Payment gateway charge failed for reservation {}", request.getReservationReference(), ex);
            throw new PaymentGatewayException("Payment gateway charge failed", ex);
        }
    }
}
