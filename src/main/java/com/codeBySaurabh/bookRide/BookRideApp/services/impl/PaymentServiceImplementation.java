package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.PaymentStrategyManager;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Payment;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentStatus;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.PaymentRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {
    private final PaymentStrategyManager paymentStrategyManager;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Ride ride) {
        Payment payment=paymentRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException(("Payment Not found for ride: "+ride.getId())));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethode()).processPayment(payment);

    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment=Payment.builder()
                .ride(ride)
                .paymentMethode(ride.getPaymentMethode())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
