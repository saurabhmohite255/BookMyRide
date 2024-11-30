package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.entities.RideRequest;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.RideRequestRepository;
import com.codeBySaurabh.bookRide.BookRideApp.services.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImplementation implements RideRequestService {
    private final RideRequestRepository rideRequestRepository;
    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId)
                .orElseThrow(()->new ResourceNotFoundException("Ride request is not found with this id :"+rideRequestId));
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.findById(rideRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Ride request is not found with id: "+rideRequest.getId()));
        rideRequestRepository.save(rideRequest);

    }
}
