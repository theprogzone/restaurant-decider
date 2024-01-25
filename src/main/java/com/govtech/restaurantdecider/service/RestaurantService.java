package com.govtech.restaurantdecider.service;

import com.govtech.restaurantdecider.dto.RestaurantRequest;
import com.govtech.restaurantdecider.dto.RestaurantResponse;

public interface RestaurantService {

    RestaurantResponse getRestaurantList(String sessionId);

    void createRestaurantChoice(RestaurantRequest restaurantRequest);
}
