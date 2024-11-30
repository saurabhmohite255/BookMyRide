package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
