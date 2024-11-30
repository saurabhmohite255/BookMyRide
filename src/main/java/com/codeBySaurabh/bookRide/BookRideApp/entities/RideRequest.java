package com.codeBySaurabh.bookRide.BookRideApp.entities;

import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.PaymentMethod;
import com.codeBySaurabh.bookRide.BookRideApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_rideRequest_rider",columnList = "rider_id")

})
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUp;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropoff;

    @CreationTimestamp
    private LocalDateTime requesttime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethode;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

    private Double fare;

}
