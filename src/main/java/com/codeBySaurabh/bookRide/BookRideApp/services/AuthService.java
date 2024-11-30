package com.codeBySaurabh.bookRide.BookRideApp.services;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.SignUpDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.UserDto;


public interface AuthService {
    String[] login(String email, String password);

    UserDto signup(SignUpDto signUpDto);

    DriverDto onboardNewDriver(Long userId, String vehicleId);

    String refreshToken(String refreshToken);
}
