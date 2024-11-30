package com.codeBySaurabh.bookRide.BookRideApp.dto;

import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionMethode;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class WalletTransactionDto {

    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethode transactionMethode;


    private RideDto rideDto;


    private WalletDto walletDto;

    private String transactionId;


    private LocalDateTime timestamp;
}
