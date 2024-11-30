package com.codeBySaurabh.bookRide.BookRideApp.Strategies;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.CashPaymentStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.WalletPaymentStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final WalletPaymentStrategy walletPaymentStrategy;
    private  final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
