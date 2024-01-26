package com.govtech.restaurantdecider.controller;

import com.govtech.restaurantdecider.dto.RestaurantRequest;
import com.govtech.restaurantdecider.dto.RestaurantResponse;
import com.govtech.restaurantdecider.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> addRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        restaurantService.createRestaurantChoice(restaurantRequest);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<RestaurantResponse> getRestaurants(@RequestParam("sessionId") String sessionId) {
        return new ResponseEntity<>(restaurantService.getRestaurantList(sessionId), HttpStatus.OK);
    }
}
