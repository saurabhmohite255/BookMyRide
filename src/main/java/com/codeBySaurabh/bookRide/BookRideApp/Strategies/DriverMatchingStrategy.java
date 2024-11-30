package com.codeBySaurabh.bookRide.BookRideApp.Strategies;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
