package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Rider;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideRequestStatus;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideStatus;
import com.codeBySaurabh.bookRide.BookRideApp.repository.RideRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.RideRequestService;
import com.codeBySaurabh.bookRide.BookRideApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImplementation implements RideService {
    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(()->new RuntimeException("ride not found with id: "+rideId));
    }


    @Override
    public Ride creatNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride=modelMapper.map(rideRequest,Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOtp());
        ride.setId(null);
        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);

    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);

    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRider(rider,pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
         return rideRepository.findByDriver(driver,pageRequest);
    }

    private String generateRandomOtp(){
        Random random=new Random();
        int otpInt= random.nextInt(1000);
        return String.format("%04d",otpInt);  //return the number from 0 to 9999
    }
}