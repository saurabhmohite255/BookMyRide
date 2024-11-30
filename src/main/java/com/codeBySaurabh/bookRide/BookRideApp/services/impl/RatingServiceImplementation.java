package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.RiderDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Rating;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Ride;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Rider;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.exception.RuntimeConflictException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.DriverRepository;
import com.codeBySaurabh.bookRide.BookRideApp.repository.RatingRepository;
import com.codeBySaurabh.bookRide.BookRideApp.repository.RiderRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImplementation implements RatingService {
    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rider rider=ride.getRider();
        Rating ratingObj=ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Rating is not found with ride id: "+ride.getId()));

        if(ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider has been Already Rated");
        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating=ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);
        rider.setRating(newRating);
        Rider saveRider=riderRepository.save(rider);
        return modelMapper.map(saveRider, RiderDto.class);


    }

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Driver driver=ride.getDriver();
        Rating ratingObj=ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Rating is not found with ride id: "+ride.getId()));

        if(ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver has been Already Rated");

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating=ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);
        driver.setRating(newRating);
        Driver saveDriver=driverRepository.save(driver);
        return modelMapper.map(saveDriver, DriverDto.class);

    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating=Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);

    }
}
