package com.codeBySaurabh.bookRide.BookRideApp.Strategies;

import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;

public interface RideFareCalculateStrategy {
     double RIDE_FARE_MULTIPLIER=10;
    double calculateFare(RideRequest rideRequest);
}
