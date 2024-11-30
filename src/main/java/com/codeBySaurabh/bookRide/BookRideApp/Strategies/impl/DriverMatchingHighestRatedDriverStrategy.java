package com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.DriverMatchingStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {

        return driverRepository.findNearestTopRatedDriver(rideRequest.getPickUp());
    }
}
