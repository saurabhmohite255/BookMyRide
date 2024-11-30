package com.codeBySaurabh.bookRide.BookRideApp.controller;

import com.codeBySaurabh.bookRide.BookRideApp.dto.*;
import com.codeBySaurabh.bookRide.BookRideApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
@Secured("ROLE_DRIVER")
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        return ResponseEntity.ok(driverService.acceptride(rideRequestId));
    }

    @PostMapping("/startRide/{rideRequestId}")
    ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                       @RequestBody RideStartDto rideStartDto){
        return ResponseEntity.ok(driverService.startRide(rideRequestId,rideStartDto.getOtp()));
    }

    @PostMapping("/endRide/{rideId}")
    ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.endRide(rideId));
    }
    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(driverService.cancelRide(rideId));
    }

    @PostMapping("rateDriver")
    public ResponseEntity<RiderDto> rateRider(@RequestBody RatingDto rateDto){
        return ResponseEntity.ok(driverService.rateRider(rateDto.getRideId(), rateDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0")Integer pageOffset,
                                                       @RequestParam(defaultValue = "10",required = false)Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageOffset,pageSize,
                Sort.by(Sort.Direction.DESC,"createdTime","id"));
        return ResponseEntity.ok(driverService.getAllMyRides(pageRequest));
    }

    @PostMapping("/rateRider/{rideId}/{rating}")
    public ResponseEntity<RiderDto> rateRider(@PathVariable Long rideId,
                                              @PathVariable Integer rating){
        return ResponseEntity.ok(driverService.rateRider(rideId,rating));
    }

}
