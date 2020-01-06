package org.javatraining.payment.controller.payment;

import org.javatraining.payment.response.CardPaymentResponse;
import org.javatraining.payment.request.CardPaymentRequest;
import org.javatraining.payment.service.CardPaymentService;
import org.javatraining.payment.service.VisaCardPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CardPaymentServiceController {
    private final Map<String, CardPaymentService> map = new HashMap<>();

    public void register(CardPaymentService service, String beanName) {
        map.put(beanName, service);
    }

    @PostMapping("payment")
    public ResponseEntity<?> processPayment(@RequestBody CardPaymentRequest request) {
        String paymentServiceName = request.getPaymentServiceName();
        CardPaymentService paymentService = map.get(paymentServiceName);
        return ResponseEntity.ok(new CardPaymentResponse(paymentService.processPayment(request)));

    }
}
