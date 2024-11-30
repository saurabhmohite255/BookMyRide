package com.codeBySaurabh.bookRide.BookRideApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_vehicle_id", columnList = "vehicleId")
        })
public class Driver {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;

        private double rating;

        private boolean available;

        private String vehicleId;

        @Column(columnDefinition = "Geometry(Point,4326)")
        private Point currentlocation;

}
