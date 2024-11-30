package com.codeBySaurabh.bookRide.BookRideApp.dto;

import com.codeBySaurabh.bookRide.BookRideApp.entities.WalletTransaction;
import lombok.Data;

import java.util.List;
@Data
public class WalletDto {

    private Long id;

    private UserDto userDto;

    private Double balance;


    private List<WalletTransaction> transactions;
}
