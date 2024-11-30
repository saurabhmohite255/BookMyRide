package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.entities.WalletTransaction;
import com.codeBySaurabh.bookRide.BookRideApp.repository.WalletTransactionRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImplementation implements WalletTransactionService {
    private final ModelMapper modelMapper;
    private final WalletTransactionRepository walletTransactionRepository;
    @Override
    public void createWalletTransaction(WalletTransaction walletTransaction) {

        walletTransactionRepository.save(walletTransaction);

    }
}
