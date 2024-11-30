package com.codeBySaurabh.bookRide.BookRideApp.repository;

import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
}
