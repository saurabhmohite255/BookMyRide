package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RideDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RiderDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideRequestStatus;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideStatus;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.DriverRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class DriverServiceImplementation implements DriverService {
    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    public RideDto cancelRide(Long rideId) {
        Ride ride=rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException(" Driver Cannot start a ride as he accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
           throw new RuntimeException("The ride cannot be cancelled,Invalid Status: "+ride.getRideStatus());
        }
        rideService.updateRideStatus(ride,RideStatus.CANCELLED);
        updateDriversAvailability(driver,true);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId,String otp) {
        Ride ride=rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException(" Driver Cannot start a ride as he accepted a request");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride status is not Confirmed hence cannot be started the status is:"+ride.getRideStatus());
        }

        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("otp is not valid: "+otp);
        }
        ride.setStartAt(LocalDateTime.now());

        Ride saveRide=rideService.updateRideStatus(ride,RideStatus.ONGOING);

        paymentService.createNewPayment(saveRide);
        ratingService.createNewRating(saveRide);

        return modelMapper.map(saveRide,RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        Ride ride=rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException(" Driver Cannot start a ride as he accepted a request");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride status is not Ongoing hence cannot be started the status is:"+ride.getRideStatus());
        }
        ride.setEndAt(LocalDateTime.now());
        Ride saveRide=rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriversAvailability(driver,true);

        paymentService.processPayment(ride);

        return modelMapper.map(saveRide,RideDto.class);
    }

    @Override
    @Transactional
    public RideDto acceptride(Long rideRequestId) {
        RideRequest rideRequest=rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride Request Status cannot be accepted status is :"+rideRequest.getRideRequestStatus());
        }
        Driver currentDriver=getCurrentDriver();
        if(!currentDriver.isAvailable()){
            throw new RuntimeException("Driver is not accepting the ride due to unavailability");

        }
        Driver savedDriver=updateDriversAvailability(currentDriver,false);
        Ride ride=rideService.creatNewRide(rideRequest,savedDriver);
        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride=rideService.getRideById(rideId);
        Driver driver=getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException(" Driver Cannot own this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)){
            throw new RuntimeException("Ride status is not Ended hence cannot give ratings, the status is:"+ride.getRideStatus());
        }
        return ratingService.rateRider(ride,rating);

    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver=getCurrentDriver();

        return modelMapper.map(currentDriver,DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver driver=getCurrentDriver();

        return rideService.getAllRidesOfDriver(getCurrentDriver(), pageRequest)
                .map(ride -> modelMapper.map(ride,RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(()-> new  ResourceNotFoundException(
                "Driver is not associated with User with id:"+user.getId()
        ));
    }

    @Override
    public Driver updateDriversAvailability(Driver driver, boolean available) {

        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }
}
