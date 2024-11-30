package com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.PaymentStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Payment;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Rider;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentStatus;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionMethode;
import com.codeBySaurabh.bookRide.BookRideApp.repository.PaymentRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//Rider had 250 rs in wallet And Driver had 500
//Ride cost is 100
//Rider wallet=250-100
//Driver wallet=500+(100-30)-> 30 rs commission

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;
    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver=payment.getRide().getDriver();
        Rider rider=payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),null,
                payment.getRide(), TransactionMethode.RIDE);

        double driverCut= payment.getAmount()*(1-PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),driverCut,null,
                payment.getRide(),TransactionMethode.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
