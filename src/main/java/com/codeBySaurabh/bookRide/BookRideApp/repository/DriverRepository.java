package com.codeBySaurabh.bookRide.BookRideApp.repository;

import com.codeBySaurabh.bookRide.BookRideApp.entities.Driver;
import com.codeBySaurabh.bookRide.BookRideApp.entities.User;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//ST_Distrance(current location,pickUp)
//ST_DWithin(pickUP,10000)
@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    @Query(value = "SELECT d.*, ST_Distance(d.currentlocation, :pickUp) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.currentlocation, :pickUp, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10" , nativeQuery = true
    )
    List<Driver> findTenNearestMatchingDriver(Point pickUp);

    @Query(value = "SELECT d.* " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.currentlocation, :pickUp, 15000) " +
            "ORDER By d.rating DESC " +
            "LIMIT 10" , nativeQuery = true
    )
    List<Driver> findNearestTopRatedDriver(Point pickUp);

    Optional<Driver> findByUser(User user);
}
