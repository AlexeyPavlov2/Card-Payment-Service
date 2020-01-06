package org.javatraining.payment.service;

import org.javatraining.payment.request.CardPaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class MirCardPaymentService implements CardPaymentService {

    @Override
    public boolean processPayment(CardPaymentRequest request) {
        System.out.println("MirCardPaymentService started");
        printDetail(request);
        return true;
    }

    @Override
    public String getName() {
        return "mirCardPaymentService";
    }
}
