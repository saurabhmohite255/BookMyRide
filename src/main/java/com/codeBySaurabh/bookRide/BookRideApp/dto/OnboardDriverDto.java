package com.codeBySaurabh.bookRide.BookRideApp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OnboardDriverDto {
     @NotBlank(message = "vehicle Id Should not blank")
    private  String vehicleId;
}
