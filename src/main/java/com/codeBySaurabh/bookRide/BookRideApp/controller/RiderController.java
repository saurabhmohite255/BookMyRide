package com.codeBySaurabh.bookRide.BookRideApp.controller;

import com.codeBySaurabh.bookRide.BookRideApp.dto.*;
import com.codeBySaurabh.bookRide.BookRideApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
@Secured("ROLE_RIDER")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestride")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto){
       return ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }

    @PostMapping("/cancelRide/{rideId}")
    public ResponseEntity<RideDto> cancelRide(@PathVariable Long rideId){
        return ResponseEntity.ok(riderService.cancelRide(rideId));
    }

    @PostMapping("rateDriver")
    public ResponseEntity<DriverDto> rateDriver(@RequestBody RatingDto rateDto){
        return ResponseEntity.ok(riderService.rateDriver(rateDto.getRideId(), rateDto.getRating()));
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<RiderDto> getProfile(){
        return ResponseEntity.ok(riderService.getMyProfile());
    }

    @GetMapping("/getMyRides")
    public ResponseEntity<Page<RideDto>> getMyALlRides(@RequestParam(defaultValue = "0")Integer pageOffset,
                                                       @RequestParam(defaultValue = "10",required = false)Integer pageSize){
        PageRequest pageRequest=PageRequest.of(pageOffset,pageSize);
        return ResponseEntity.ok(riderService.getMyAllRides(pageRequest));
    }
    @PostMapping("/rateDriver/{rideId}/{rating}")
    public ResponseEntity<DriverDto> rateDriver(@PathVariable Long rideId,
                                              @PathVariable Integer rating){
        return ResponseEntity.ok(riderService.rateDriver(rideId,rating));
    }

}
