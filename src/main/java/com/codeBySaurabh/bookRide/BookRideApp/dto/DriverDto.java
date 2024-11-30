package com.codeBySaurabh.bookRide.BookRideApp.dto;

import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private Long id;

    private User user;

    private double rating;

    private boolean available;

    private String vehicleId;

}
