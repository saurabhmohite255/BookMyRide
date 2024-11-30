package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Payment;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
