package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RideDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RideRequestDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RiderDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Rider;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {
    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);


    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getMyAllRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
