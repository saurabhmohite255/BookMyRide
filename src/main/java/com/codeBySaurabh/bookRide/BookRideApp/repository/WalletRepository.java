package com.codeBySaurabh.bookRide.BookRideApp.repository;

import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
   Optional<Wallet> findByUser(User user);
}
