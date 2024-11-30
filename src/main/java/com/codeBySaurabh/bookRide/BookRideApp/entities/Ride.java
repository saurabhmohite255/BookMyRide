package com.codeBySaurabh.bookRide.BookRideApp.entities;

import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentMethod;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideRequestStatus;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_ride_rider",columnList = "rider_id"),
        @Index(name = "idx_ride_driver",columnList = "driver_id")

})
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickup;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropoff;

    @CreationTimestamp
    private LocalDateTime createdtime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethode;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

}
