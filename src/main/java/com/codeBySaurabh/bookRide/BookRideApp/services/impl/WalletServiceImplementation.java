package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Wallet;
import com.codeBySaurabh.bookRide.BookRideApp.entities.WalletTransaction;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionMethode;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionType;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.WalletRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.WalletService;
import com.codeBySaurabh.bookRide.BookRideApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletServiceImplementation implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethode transactionMethode) {
        Wallet wallet=findByUser(user);

        wallet.setBalance(wallet.getBalance()+amount);

        WalletTransaction walletTransaction=WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethode(transactionMethode)
                .amount(amount)
                .build();
        walletTransactionService.createWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethode transactionMethode) {
        Wallet wallet=findByUser(user);

        wallet.setBalance(wallet.getBalance()-amount);

        WalletTransaction walletTransaction=WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethode(transactionMethode)
                .amount(amount)
                .build();
        //walletTransactionService.createWalletTransaction(walletTransaction);
        wallet.getTransactions().add(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withdrawAllMYMoneyFromWallet() {

    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet=new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(()->new ResourceNotFoundException("Wallet is not found with id: "+walletId));
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("Wallet Not found with useId: "+user.getId()));
    }
}
