package com.codeBySaurabh.bookRide.BookRideApp.services.impl;

import com.codeBySaurabh.bookRide.BookRideApp.services.DistanceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistanceServiceOSRMImplementation implements DistanceService {

    private static final String OSRM_API_BAE_URL="https://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point src, Point dest) {
        String uri= src.getX()+","+ src.getY()+";"+ dest.getX()+","+ dest.getY();
        try {
            OSRMResponseDto osrmResponse= RestClient.builder()
                    .baseUrl(OSRM_API_BAE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDto.class);
            return osrmResponse.getRoutes().get(0).getDistance() / 1000.0;
        }catch (Exception e){
            throw new RuntimeException("Error getting data from OSRM"+e.getMessage());
        }

    }
}
@Data
class OSRMResponseDto{
    private List<OSRMRoute> routes;

}
@Data
class OSRMRoute{
    private Double distance;

}
