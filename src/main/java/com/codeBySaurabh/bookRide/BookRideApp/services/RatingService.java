package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RiderDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;

public interface RatingService {

    RiderDto rateRider(Ride ride, Integer rating);

    DriverDto rateDriver(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
