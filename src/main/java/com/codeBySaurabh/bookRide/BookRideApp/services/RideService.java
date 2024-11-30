package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Rider;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface RideService {
    Ride getRideById(Long rideId);


    Ride creatNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride rideId, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}
