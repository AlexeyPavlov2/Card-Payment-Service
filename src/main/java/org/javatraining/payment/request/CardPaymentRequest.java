package org.javatraining.payment.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public  class CardPaymentRequest {
    private String paymentServiceName;

    private String cardNumber;

    private String cardHolder;

    private String paymentReceiver;

    protected BigDecimal amount;

}
