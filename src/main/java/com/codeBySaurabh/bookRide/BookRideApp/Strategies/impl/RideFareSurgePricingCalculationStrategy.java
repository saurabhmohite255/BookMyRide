package com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.RideFareCalculateStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.services.DistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingCalculationStrategy implements RideFareCalculateStrategy {
    private final DistanceService distanceService;
    private static final double SURGE_FACTOR=2;
    @Override
    public double calculateFare(RideRequest rideRequest) {

        double distance=distanceService.calculateDistance(rideRequest.getPickUp(),rideRequest.getDropoff());

        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;

    }
}
