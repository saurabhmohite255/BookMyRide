package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Wallet;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionMethode;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethode transactionMethode);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethode transactionMethode);

    void withdrawAllMYMoneyFromWallet();

    Wallet createNewWallet(User user);

    Wallet findWalletById(Long walletId);

    Wallet findByUser(User user);
}
