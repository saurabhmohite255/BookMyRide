package com.codeBySaurabh.bookRide.BookRideApp.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String name;
    @Email(message = "You should have pass a valid email")
    private String email;
    private String password;
}
