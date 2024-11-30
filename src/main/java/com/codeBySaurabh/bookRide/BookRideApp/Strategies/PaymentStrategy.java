package com.codeBySaurabh.bookRide.BookRideApp.Strategies;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION=0.3;
    void processPayment(Payment payment);
}
