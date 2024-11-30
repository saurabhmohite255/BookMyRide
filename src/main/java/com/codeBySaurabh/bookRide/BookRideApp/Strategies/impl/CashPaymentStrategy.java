package com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.PaymentStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Payment;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentStatus;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionMethode;
import com.codeBySaurabh.bookRide.BookRideApp.repository.PaymentRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//Rider Pay=100
//Driver=70 and 30rs deduct from drivers wallet
@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();


        double platformCommission= payment.getAmount()*PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(),platformCommission,null,payment.getRide(), TransactionMethode.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
//Wallet driverWallet=walletService.findByUser(driver.getUser());