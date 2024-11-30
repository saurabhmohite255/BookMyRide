package com.codeBySaurabh.bookRide.BookRideApp.dto;

import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentMethod;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private Long id;

    private PointDto pickup;

    private PointDto dropoff;

    private LocalDateTime createdtime;

    private RiderDto rider;

    private Double fare;

    private DriverDto driver;

    private PaymentMethod paymentMethode;

    private RideRequestStatus rideRequestStatus;
}
