package com.codeBySaurabh.bookRide.BookRideApp.Strategies;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.DriverMatchingNearestDriverMatchingStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.RideFareDefaultPricingCalculationStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.RideFareSurgePricingCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {
    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverMatchingStrategy driverMatchingNearestDriverMatchingStrategy;
    private final RideFareSurgePricingCalculationStrategy rideFareSurgePricingCalculationStrategy;
    private final RideFareDefaultPricingCalculationStrategy rideFareDefaultPricingCalculationStrategy;
    public DriverMatchingStrategy driverMatchingStrategy(double rating){
        if(rating>=4.0){
            return driverMatchingHighestRatedDriverStrategy;
        }else {
            return driverMatchingNearestDriverMatchingStrategy;
        }

    }
    public RideFareCalculateStrategy rideFareCalculateStrategy(){
        LocalTime surgeStartTime= LocalTime.of(18,30);
        LocalTime surgeEndTime= LocalTime.of(21,30);
        LocalTime currentTime= LocalTime.now();

        boolean isBetweenSurgeTime= currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isBetweenSurgeTime){
            return rideFareSurgePricingCalculationStrategy;
        }else {
            return rideFareDefaultPricingCalculationStrategy;
        }

    }
}
