package com.codeBySaurabh.bookRide.BookRideApp.entities;

import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionMethode;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_walletTransaction_wallet",columnList = "wallet_id"),
        @Index(name = "idx_walletTransaction_ride",columnList = "ride_id")

})
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethode transactionMethode;

    @ManyToOne
    private Ride ride;

    @ManyToOne
    private Wallet wallet;

    private String transactionId;

    @CreationTimestamp
    private LocalDateTime timestamp;
}
