package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.Strategies.RideStrategyManager;
import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.DriverMatchingNearestDriverMatchingStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.Strategies.impl.RideFareDefaultPricingCalculationStrategy;
import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RideDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RideRequestDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RiderDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.*;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideRequestStatus;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideStatus;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.RideRequestRepository;
import com.codeBySaurabh.bookRide.BookRideApp.repository.RiderRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.DriverService;
import com.codeBySaurabh.bookRide.BookRideApp.services.RatingService;
import com.codeBySaurabh.bookRide.BookRideApp.services.RideService;
import com.codeBySaurabh.bookRide.BookRideApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RiderServiceImplementation implements RiderService {

    private final ModelMapper modelMapper;

    private final RiderRepository riderRepository;

    private final RideStrategyManager rideStrategyManager;

    private final RideFareDefaultPricingCalculationStrategy rideFareCalculateStrategy;

    private final DriverMatchingNearestDriverMatchingStrategy driverMatchingStrategy;

    private final RideRequestRepository rideRequestRepository;

    private final RideService rideService;
    private final DriverService driverService;
    private  final RatingService ratingService;

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider=getCurrentRider();
        RideRequest rideRequest=modelMapper.map(rideRequestDto,RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare=rideStrategyManager.rideFareCalculateStrategy().calculateFare(rideRequest);

        rideRequest.setFare(fare);

        RideRequest saveRideRequest=rideRequestRepository.save(rideRequest);

       List<Driver> drivers= rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
       //TODO Send notification to all drivers about this ride request

        return modelMapper.map(saveRideRequest,RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        Rider rider=getCurrentRider();
        Ride ride=rideService.getRideById(rideId);

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException("Rider does not own this ride with id: "+rideId);
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("The ride cannot be cancelled,Invalid Status: "+ride.getRideStatus());
        }
       Ride savedRide= rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        driverService.updateDriversAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide,RideDto.class);


    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {

        Ride ride=rideService.getRideById(rideId);
        Rider rider=getCurrentRider();

        if(!rider.equals(ride.getRider())){
            throw new RuntimeException(" Driver Cannot own this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not Ended hence cannot give ratings, the status is:"+ride.getRideStatus());
        }
        return ratingService.rateDriver(ride,rating);

    }

    @Override
    public RiderDto getMyProfile() {
        Rider currentRider=getCurrentRider();

        return modelMapper.map(currentRider,RiderDto.class);
    }

    @Override
    public Page<RideDto> getMyAllRides(PageRequest pageRequest) {
       Rider currentRider=getCurrentRider();

        return rideService.getAllRidesOfRider(currentRider, pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }


    @Override
    public Rider createNewRider(User user) {
        Rider rider=Rider.builder()
                .user(user)
                .rating(0.00)
                .build();
        return riderRepository.save(rider);

    }

    @Override
    public Rider getCurrentRider() {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user).orElseThrow(()-> new  ResourceNotFoundException(
                "rider is not associated with User with id:"+user.getId()
        ));
    }
}
