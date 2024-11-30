package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.dto.DriverDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.SignUpDto;
import com.codeBySaurabh.bookRide.BookRideApp.dto.UserDto;
import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.Role;
import com.codeBySaurabh.bookRide.BookRideApp.exception.ResourceNotFoundException;
import com.codeBySaurabh.bookRide.BookRideApp.exception.RuntimeConflictException;
import com.codeBySaurabh.bookRide.BookRideApp.repository.UserRepository;
import com.codeBySaurabh.bookRide.BookRideApp.security.JWTService;
import com.codeBySaurabh.bookRide.BookRideApp.services.AuthService;
import com.codeBySaurabh.bookRide.BookRideApp.services.DriverService;
import com.codeBySaurabh.bookRide.BookRideApp.services.RiderService;
import com.codeBySaurabh.bookRide.BookRideApp.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.codeBySaurabh.bookRide.BookRideApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImplementation implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private  final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    @Override
    public String[] login(String email, String password) {

        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );
        User user= (User) authentication.getPrincipal();

        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
    }

    @Override
    public UserDto signup(SignUpDto signUpDto) {
       User user= userRepository.findByEmail(signUpDto.getEmail()).orElse(null);
        if(user!=null){
            throw new RuntimeConflictException("Cannot signUp! User with this email already present"+ signUpDto.getEmail());
        }


        User mappedUser=modelMapper.map(signUpDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser=userRepository.save(mappedUser);

        //create user related entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId,String vehicleId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user is not found with id: "+userId));

        if(user.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("User with id "+userId+" is already Driver");

        Driver createDriver= Driver.builder()
                .user(user)
                .rating(0.0)
                .available(true)
                .vehicleId(vehicleId)
                .build();
        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver saveDriver=driverService.createNewDriver(createDriver);

        return modelMapper.map(saveDriver,DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId= jwtService.getUserIdFromToken(refreshToken);
        User user=userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user is not found with id: "+userId));
        return jwtService.generateAccessToken(user);
    }
}
