package org.javatraining.payment.service;

import org.javatraining.payment.request.CardPaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class MasterCardPaymentService implements CardPaymentService {

    @Override
    public boolean processPayment(CardPaymentRequest request) {
        System.out.println("MasterCardPaymentService started");
        printDetail(request);
        return true;
    }

    @Override
    public String getName() {
        return "masterCardPaymentService";
    }
}
