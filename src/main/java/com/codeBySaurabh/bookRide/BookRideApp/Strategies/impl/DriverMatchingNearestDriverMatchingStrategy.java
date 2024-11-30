package com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.DriverMatchingStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.repository.DriverRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class DriverMatchingNearestDriverMatchingStrategy implements DriverMatchingStrategy {
    private final DriverRepository driverRepository;
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {

        return driverRepository.findTenNearestMatchingDriver(rideRequest.getPickUp());
    }
}
