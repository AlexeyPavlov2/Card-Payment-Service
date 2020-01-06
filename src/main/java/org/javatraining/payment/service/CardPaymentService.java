package org.javatraining.payment.service;

import org.javatraining.payment.controller.payment.CardPaymentServiceController;
import org.javatraining.payment.request.CardPaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

public interface CardPaymentService {

    @Autowired
    default void registerService(CardPaymentServiceController controller) {
        controller.register(this, getName() );
    }

    boolean processPayment(CardPaymentRequest request);

    default void printDetail(CardPaymentRequest request) {
        final String message =
                "Payment service name: {0}\nCard number: {1}\nCard holder name: {2}\nReceiver: {3}\nAmount: {4}";
        System.out.println(MessageFormat.format(message, new String[]
                {request.getPaymentServiceName(), request.getCardNumber(), request.getCardHolder(),
                        request.getPaymentReceiver(), request.getAmount().toString()}));
    }

    String getName();

}
