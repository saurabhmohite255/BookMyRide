package com.codeBySaurabh.bookRide.BookRideApp.repository;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Rider;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long> {
    Optional<Rider> findByUser(User user);
}
