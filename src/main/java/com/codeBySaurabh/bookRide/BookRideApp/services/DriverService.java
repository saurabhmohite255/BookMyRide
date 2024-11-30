package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RideDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RiderDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DriverService {
    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId,String otp);

    RideDto endRide(Long rideId);

    RideDto acceptride(Long rideRequestId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriversAvailability(Driver driver, boolean available);

    Driver createNewDriver(Driver driver);
}
